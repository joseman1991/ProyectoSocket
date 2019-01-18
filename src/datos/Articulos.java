package datos;

import java.sql.SQLException;


// Generated 26-ago-2017 15:00:10 by Hibernate Tools 4.3.1

/**
 * Articulos generated by hbm2java
 */
public class Articulos implements java.io.Serializable {

    private int codcategoria;
    private int codsubcategoria;
    private String codarticulo;
    private Categorias categorias;
    private Subcategorias subcategorias;
    private String descripcion;
    private Character grabaiva;
    private double costo;
    private double pvp;
    private double stock;
    private Character codestado;
    private Estado estado;

    public Articulos() {
    }

    public String getCodarticulo() {
        return this.codarticulo;
    }

    public void setCodarticulo(String codarticulo) {
        this.codarticulo = codarticulo;
    }

    public Categorias getCategorias() {
        return this.categorias;
    }

    public void setCategorias(Categorias categorias) {
        this.categorias = categorias;
    }

    public Subcategorias getSubcategorias() {
        return this.subcategorias;
    }

    public void setSubcategorias(Subcategorias subcategorias) {
        this.subcategorias = subcategorias;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Character getGrabaiva() {
        return this.grabaiva;
    }

    public void setGrabaiva(Character grabaiva) {
        this.grabaiva = grabaiva;
    }

    public double getCosto() {
        return this.costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getPvp() {
        return this.pvp;
    }

    public void setPvp(double pvp) {
        this.pvp = pvp;
    }

    public double getStock() {
        return this.stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public Character getCodestado() {
        return this.codestado;
    }

    public void setCodestado(Character codestado) throws SQLException {
        estado= new EstadoDAO().getObtnerEstado(codestado);
        this.codestado = codestado;
    }

    public int getCodcategoria() {
        return codcategoria;
    }

    public void setCodcategoria(int codcategoria) throws SQLException {
        categorias= new CategoriasDAO().getCategorias(codcategoria);
        this.codcategoria = codcategoria;
    }

    public int getCodsubcategoria() {
        return codsubcategoria;
    }

    public void setCodsubcategoria(int codsubcategoria) throws SQLException {
        subcategorias= new SubCategoriasDAO().geSubtCategorias(codsubcategoria);
        this.codsubcategoria = codsubcategoria;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

}
