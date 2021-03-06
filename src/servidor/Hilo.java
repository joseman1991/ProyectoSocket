/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import datos.DataSet;
import datos.Perfiles;
import datos.PerfilesDAO;
import datos.UsuarioDAO;
import datos.Usuarios;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JOSE
 */
public class Hilo implements Runnable {

    private final List<Perfiles> listaPerfiles;
    private final PerfilesDAO perfilesDAO;
    private final UsuarioDAO usuarioDAO;
    private ServerSocket socketServer;
    private Usuarios us;

    private ObjectOutputStream outO;
    private ObjectInputStream inO;

    public Hilo(ServerSocket socket) {
        this.socketServer = socket;
        perfilesDAO = new PerfilesDAO();
        usuarioDAO = new UsuarioDAO();
        listaPerfiles = new ArrayList<>();
    }

    @Override
    public void run() {
        System.out.println("escuchando puerto " + socketServer.getLocalPort());
        while (true) {
            try {
                Socket socket = socketServer.accept();
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in;
                in = new DataInputStream(socket.getInputStream());
                String c = in.readUTF();
                switch (c) {

                    case "d":
                        System.out.println(String.format("Dato recibido de %s desde puerto %d", socket.getInetAddress().getHostName(), socket.getLocalPort()));
                        inO = new ObjectInputStream(socket.getInputStream());
                        us = (Usuarios) inO.readObject();
                        out.writeUTF("Hola, " + us.getNombres() + " " + us.getApellidos());
                        break;

                    case "lg":
                        inO = new ObjectInputStream(socket.getInputStream());
                        us = (Usuarios) inO.readObject();
                        loggin(us);
                        outO = new ObjectOutputStream(socket.getOutputStream());
                        outO.writeObject(us);
                        break;

                    case "l":
                        obtenerPerfiles();
                        outO = new ObjectOutputStream(socket.getOutputStream());
                        outO.writeObject(listaPerfiles);
                        break;

                    case "i":
                        inO = new ObjectInputStream(socket.getInputStream());
                        Usuarios u = (Usuarios) inO.readObject();
                        int res = insertarUser(u);
                        out.writeInt(res);
                        break;

                    case "u":
                        outO = new ObjectOutputStream(socket.getOutputStream());
                        DataSet lista = obtenerUser();
                        outO.writeObject(lista);
                        break;

                    case "t":
                        int puerto = in.readInt();
                        boolean estado = test(puerto);
                        out.writeBoolean(estado);
                        break;

                    case "g":
                        inO = new ObjectInputStream(socket.getInputStream());
                        Perfiles p = (Perfiles) inO.readObject();
                        boolean status = test(p.getPuerto());
                        out.writeBoolean(status);
                        if (status) {
                            cambiar(p);
                            System.out.println("Puerto cambiado a " + socketServer.getLocalPort());
                        }
                        break;

                    case "ok":
                        out.writeBoolean(true);
                        break;

                    case "lok":
                        System.out.println("Usuario logueado desde puerto: " + socketServer.getLocalPort());
                        break;

                    default:
                        System.out.println("enviaste " + c);
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void loggin(Usuarios u) {
        try {
            usuarioDAO.obtenerRegistro(u);
            Perfiles p = new Perfiles();
            p.setIdperfil(u.getIdperfil());
            perfilesDAO.obtenerRegistro(p);
            u.setPerfil(p);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean test(int puerto) {
        try {
            ServerSocket s = new ServerSocket(puerto);
            s.close();
            return true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Puerto no disponible");
            return false;
        }
    }

    private int cambiar(Perfiles perfiles) throws IOException {
        socketServer.close();
        socketServer = new ServerSocket(perfiles.getPuerto());
        int res = 0;
        try {
            perfilesDAO.actualizarRegistro(perfiles);
            System.out.println("puerto cambiado");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            res = -1;
        }

        return res;
    }

    private void obtenerPerfiles() {
        try {
            listaPerfiles.clear();
            perfilesDAO.obtenerLista(listaPerfiles);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            perfilesDAO.cerrarConexion();
        }
    }

    private int insertarUser(Usuarios u) {
        int res = -1;
        try {
            res = usuarioDAO.insertarRegistro(u);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            usuarioDAO.cerrarConexion();
        }
        return res;
    }

    private DataSet obtenerUser() {
        DataSet res = null;
        try {
            res = usuarioDAO.dt();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            usuarioDAO.cerrarConexion();
        }
        return res;
    }

}
