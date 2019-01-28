package datos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JOSE-MA
 */
public class ArticuloDAO extends ConexionMySQL {

    private final List<Articulos> listaArticulos;

    private String mensaje;
    private String Subcategoria;

    public ArticuloDAO() {
        listaArticulos = new ArrayList<>();
    }

    public ArticuloDAO(List<Articulos> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }
    
    

    public float obtenerIVA() throws SQLException {
        float iva = 0;
        abrirConexion();
        sentencia = conexion.prepareStatement("select iva from empresa");
        resultado = sentencia.executeQuery();
        if (resultado.next()) {
            iva = resultado.getFloat(1);
        }
        cerrarConexion();
        return iva;
    }

    public void obtenerArticulos() throws SQLException {
        listaArticulos.clear();
        abrirConexion();
        sentencia = conexion.prepareStatement("select * from articulos");
        resultado = sentencia.executeQuery();
        while (resultado.next()) {
            Articulos art = new Articulos();
            int i = 1;
            art.setCodcategoria(resultado.getInt(i++));
            art.setCodsubcategoria(resultado.getInt(i++));
            art.setCodarticulo(resultado.getString(i++));
            art.setDescripcion(resultado.getString(i++));
            art.setGrabaiva(resultado.getString(i++).charAt(0));
            art.setCosto(resultado.getDouble(i++));
            art.setPvp(resultado.getDouble(i++));
            art.setStock(resultado.getDouble(i++));
            art.setCodestado(resultado.getString(i++).charAt(0));
            listaArticulos.add(art);
        }
        cerrarConexion();
    }

    public DataSet obtenerArticulos(String busca) throws SQLException {
        DataSet dt = new DataSet();
        abrirConexion();
        sentencia = conexion.prepareStatement("select codarticulo as Codigo, descripcion as producto, (costo) as costo, pvp, stock, estado from articulos  "
                + " where codarticulo like ? or  descripcion like ? or descripcion like ? "
                + " order by codarticulo");
        sentencia.setString(1, busca + "%");
        sentencia.setString(2, busca + "%");
        sentencia.setString(3, "%" + busca + "%");
        resultado = sentencia.executeQuery();
        dt.load(resultado);
        cerrarConexion();
        return dt;
    }

    public DataSet obtenerArticulos(String busca, int codc) throws SQLException {
        DataSet dt = new DataSet();
        abrirConexion();
        sentencia = conexion.prepareStatement("select codarticulo as Codigo, descripcion as producto, round(costo,2), pvp, stock, estado from articulos  "
                + " where (codarticulo like ? or  descripcion like ? or descripcion like ?) and codsubcategoria=?"
                + " order by codarticulo");
        sentencia.setString(1, busca + "%");
        sentencia.setString(2, busca + "%");
        sentencia.setString(3, "%" + busca + "%");
        sentencia.setInt(4, codc);
        resultado = sentencia.executeQuery();
        dt.load(resultado);
        cerrarConexion();
        return dt;
    }

    public DataSet obtenerArticulosPro(Responsables res) throws SQLException {
        DataSet dt = new DataSet();
        abrirConexion();
        sentencia = conexion.prepareStatement("select fechamovimiento, a.codarticulo, descripcion, cantidad, dtm.costo from articulos as a,detallemovimiento as dtm, movimientos as m\n"
                + "where a.codarticulo=dtm.codarticulo and m.codmovimiento=dtm.codmovimiento and m.codtiporesponsable=? AND m.identificacion=?");
        sentencia.setString(1, res.getCodtiporesposanble());
        sentencia.setString(2, res.getIdentificacion());
        resultado = sentencia.executeQuery();
        dt.load(resultado);
        cerrarConexion();
        return dt;
    }

    public Articulos obtenerArticulo(String codArt) throws SQLException {
        Articulos art = null;
        abrirConexion();
        sentencia = conexion.prepareStatement("select * from articulos where codarticulo=?");
        sentencia.setString(1, codArt);
        resultado = sentencia.executeQuery();
        if (resultado.next()) {
            art = new Articulos();
            int i = 1;
            art.setCodcategoria(resultado.getInt(i++));
            art.setCodsubcategoria(resultado.getInt(i++));
            art.setCodarticulo(resultado.getString(i++));
            art.setDescripcion(resultado.getString(i++));
            art.setGrabaiva(resultado.getString(i++).charAt(0));
            art.setCosto(resultado.getDouble(i++));
            art.setPvp(resultado.getDouble(i++));
            art.setStock(resultado.getDouble(i++));
            art.setCodestado(resultado.getString(i++).charAt(0));
        }
        cerrarConexion();
        return art;
    }

    public void actualizarStock(List<Articulos> listaArticulos) throws SQLException {
        abrirConexion();
        for (int i = 0; i < listaArticulos.size(); i++) {
            Articulos get = listaArticulos.get(i);
            sentencia = conexion.prepareStatement("update articulos set stock=stock+? where codarticulo=?");
            sentencia.setDouble(1, get.getStock());
            sentencia.setString(2, get.getCodarticulo());
            sentencia.executeUpdate();
        }
        cerrarConexion();
    }

    public String nextCodigoArt() throws SQLException {
        int i = 1;
        String numero;
        abrirConexion();
        sentencia = conexion.prepareStatement("select max(codarticulo) from articulos");
        resultado = sentencia.executeQuery();
        if (resultado.next()) {
            i = resultado.getInt(1) + 1;
        }
        DecimalFormat df = new DecimalFormat("000000");
        numero = df.format(i);
        cerrarConexion();
        return numero;
    }

    private String nextCodigoArt2() throws SQLException {
        int i = 1;
        String numero;
        sentencia = conexion.prepareStatement("select max(codarticulo) from articulos");
        resultado = sentencia.executeQuery();
        if (resultado.next()) {
            i = resultado.getInt(1) + 1;
        }
        DecimalFormat df = new DecimalFormat("000000");
        numero = df.format(i);
        return numero;
    }

    public long getNextCodMovimiento() throws SQLException {
//        abrirConexion();
        long cod = 1;
        sentencia = conexion.prepareStatement("select max(codmovimiento) from movimientos");
        resultado = sentencia.executeQuery();
        if (resultado.next()) {
            cod = resultado.getLong(1) + 1;
        }
//        cerrarConexion();
        return (cod);
    }

   
    

    public int insertarArticulo(Articulos art) throws SQLException {

        abrirConexion();
        sentencia = conexion.prepareStatement("INSERT INTO articulos(codcategoria, codsubcategoria, codarticulo, "
                + " descripcion, grabaiva, "
                + " costo, pvp, stock, estado)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        int i = 1;
        sentencia.setInt(i++, art.getCodcategoria());
        sentencia.setInt(i++, art.getCodsubcategoria());
        sentencia.setString(i++, art.getCodarticulo());
        sentencia.setString(i++, art.getDescripcion().toUpperCase());
        sentencia.setString(i++, art.getGrabaiva() + "");
        sentencia.setDouble(i++, art.getCosto());
        sentencia.setDouble(i++, art.getPvp());
        sentencia.setDouble(i++, art.getStock());
        sentencia.setString(i++, art.getCodestado() + "");

        System.out.println(art.getCodcategoria());
        System.out.println(art.getCodsubcategoria());
        System.out.println(art.getCodarticulo());
        System.out.println(art.getDescripcion());
        System.out.println(art.getGrabaiva());
        System.out.println(art.getCosto());
        System.out.println(art.getPvp());
        System.out.println(art.getStock());
        System.out.println(art.getEstado());

        int exc = sentencia.executeUpdate();
        cerrarConexion();
        return exc;
    }

    private int insertarArticulo2(Articulos art) throws SQLException {

        sentencia = conexion.prepareStatement("INSERT INTO articulos(codcategoria, codsubcategoria, codarticulo, "
                + " descripcion, grabaiva, "
                + " costo, pvp, stock)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        int i = 1;

        sentencia.setInt(i++, art.getCodcategoria());
        sentencia.setInt(i++, art.getCodsubcategoria());
        sentencia.setString(i++, art.getCodarticulo());
        sentencia.setString(i++, art.getDescripcion().toUpperCase());
        sentencia.setString(i++, art.getGrabaiva() + "");
        sentencia.setDouble(i++, art.getCosto());
        sentencia.setDouble(i++, art.getPvp());
        sentencia.setDouble(i++, art.getStock());

        int exc = sentencia.executeUpdate();
        return exc;
    }

    public int actualizarArticulo(Articulos art) throws SQLException {
        abrirConexion();
        sentencia = conexion.prepareStatement("UPDATE articulos "
                + "   SET codcategoria=?, codsubcategoria=?, descripcion=?, "
                + "   grabaiva=?, costo=?, pvp=?, estado=?, stock=?"
                + " WHERE codarticulo=?");
        int i = 1;
        sentencia.setInt(i++, art.getCodcategoria());
        sentencia.setInt(i++, art.getCodsubcategoria());
        sentencia.setString(i++, art.getDescripcion().toUpperCase());
        sentencia.setString(i++, art.getGrabaiva() + "");
        sentencia.setDouble(i++, art.getCosto());
        sentencia.setDouble(i++, art.getPvp());
        sentencia.setString(i++, art.getCodestado() + "");
        sentencia.setDouble(i++, art.getStock());
        sentencia.setString(i++, art.getCodarticulo());
        int exc = sentencia.executeUpdate();
        cerrarConexion();
        return exc;
    }

    public float obtenerMinimo(String codarticulo) throws SQLException {
        abrirConexion();
        float min = 0;
        sentencia = conexion.prepareStatement("select min(stock_c) from costos where codarticulo=?");
        sentencia.setString(1, codarticulo);
        resultado = sentencia.executeQuery();
        if (resultado.next()) {
            min = resultado.getFloat(1);
        }
        cerrarConexion();
        return min;
    }

    public float obtenerMaximo(String codarticulo) throws SQLException {
        abrirConexion();
        float max = 0;
        sentencia = conexion.prepareStatement("select max(stock_c) from costos where codarticulo=?");
        sentencia.setString(1, codarticulo);
        resultado = sentencia.executeQuery();
        if (resultado.next()) {
            max = resultado.getFloat(1);
        }
        cerrarConexion();
        return max;
    }

    public Object[] obtenerBalance(Articulos articulo) throws SQLException {
        Object art[] = null;
        abrirConexion();
        sentencia = conexion.prepareStatement("select min(codsaldo), fecha, stock_c, COSTO_c from costos where "
                + "codarticulo=? group by fecha,stock_c, costo_c");
        sentencia.setString(1, articulo.getCodarticulo());
        resultado = sentencia.executeQuery();
        if (resultado.next()) {
            int i = 2;
            Date fecha = resultado.getDate(i++);
            float stock = resultado.getFloat(i++);
            float costo_c = resultado.getFloat(i++);
            float total = stock * costo_c;
            Object[] art1 = {fecha, "BALANCE INICIAL", "-", "-", "-", "-", "-", "-", stock, costo_c, total};
            art = art1;
        }
        cerrarConexion();
        return art;
    }

    public List<Articulos> getListaArticulos() {
        return listaArticulos;
    }

     

    public String getMensaje() {
        return mensaje;
    }

    public String getSubcategoria() {
        return Subcategoria;
    }

}
