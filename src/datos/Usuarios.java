/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;

/**
 *
 * @author JOSE
 */
public class Usuarios implements Serializable {

    private String nombreusuario;
    private String clave;
    private String nombres;
    private String apellidos;
    private int idperfil;
    private Perfiles perfil;

    public Usuarios() {
        super();
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getIdperfil() {
        return idperfil;
    }

    public void setIdperfil(int idperfil) {       
        this.idperfil = idperfil;
    }

    @Override
    public String toString() {
        return "Usuarios{" + "nombreusuario=" + nombreusuario + ", clave=" + clave + ", nombres=" + nombres + ", apellidos=" + apellidos + ", idperfil=" + idperfil + '}';
    }

    public Perfiles getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

}
