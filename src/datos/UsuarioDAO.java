/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author JOSE
 */
public class UsuarioDAO extends ConexionMySQL<Usuarios> {

    public UsuarioDAO() {
        tabla = "usuarios";
        tipo = Usuarios.class;
    }

    @Override
    public int insertarRegistro(Usuarios registro) throws SQLException {
        campos = "nombreusuario,clave,nombres,apellidos,idperfil";
        int resul = super.insertarRegistro(registro);
        cerrarConexion();
        return resul;
    }

    @Override
    public void obtenerLista(List<Usuarios> lista) throws SQLException {
        campos = "nombreusuario,clave,nombres,apellidos,idperfil";
        camposCondicion = "";
        condicion = "";
        cerrarConexion();
        super.obtenerLista(lista);
    }

    @Override
    public Usuarios obtenerRegistro(Usuarios dato) throws SQLException {
        campos = "nombreusuario,nombres,apellidos,idperfil";
        camposCondicion = "nombreusuario,clave";
        condicion = "where nombreusuario=? and clave=?";
        dato = super.obtenerRegistro(dato);
        cerrarConexion();
        return dato;
    }

    public DataSet dt() throws SQLException {
        abrirConexion();
        DataSet dt = new DataSet();
        sentencia = conexion.prepareStatement("select nombreusuario,nombres,apellidos,p.nombreperfil as perfil"
                + " from usuarios u inner join perfiles p on p.idperfil=u.idperfil");
        resultado = sentencia.executeQuery();
        dt.load(resultado);
        cerrarConexion();
        return dt;
    }

}
