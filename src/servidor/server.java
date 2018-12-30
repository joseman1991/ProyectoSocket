/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import datos.Perfiles;
import datos.PerfilesDAO;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author JOSE
 */
public class server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner leer = new Scanner(System.in);
        int puertoAdmin, puertoUser;
        String numero;
        Perfiles perfil = new Perfiles();
        List<Perfiles> per = new ArrayList<>();
        PerfilesDAO pdao = new PerfilesDAO();
        try {
            do {
                System.out.print("Ingresa el puerto del servidor: ");
                numero = leer.next();
                if (isNumero(numero)) {
                    puertoAdmin = Integer.parseInt(numero);
                    if (validarPuertoLocal(puertoAdmin)) {
                        perfil.setIdperfil(1);
                        perfil.setPuerto(puertoAdmin);
                        pdao.actualizarRegistro(perfil);
                    } else {
                        System.err.println("puerto inválido o en uso");
                        continue;
                    }
                } else {
                    System.err.println("Ingresa un numéro válido");
                    continue;
                }
                do {
                    System.out.print("Ingresa el puerto del usuario: ");
                    numero = leer.next();
                    if (isNumero(numero)) {
                        puertoUser = Integer.parseInt(numero);
                        if (validarPuertoLocal(puertoUser)) {
                            perfil.setIdperfil(2);
                            perfil.setPuerto(puertoUser);
                            pdao.actualizarRegistro(perfil);
                        } else {
                            System.err.println("puerto inválido o en uso");
                        }
                    } else {
                        System.err.println("Ingresa un numéro válido");
                    }
                } while (!isNumero(numero)|| !validarPuertoLocal(Integer.parseInt(numero)));
            } while (!isNumero(numero) || !validarPuertoLocal(Integer.parseInt(numero)));

            pdao.obtenerLista(per);

            for (int i = 0; i < per.size(); i++) {
                Perfiles get = per.get(i);
                ServerSocket socket1 = new ServerSocket(get.getPuerto());
                Hilo h1 = new Hilo(socket1);
                Thread t1 = new Thread(h1);
                t1.start();
            }
        } catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean isNumero(String numero) {
        try {
            Integer.parseInt(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validarPuertoLocal(int puerto) {
        ServerSocket puertoServer;
        try {
            puertoServer = new ServerSocket(puerto);
            puertoServer.close();
            return true;
        } catch (IOException | IllegalArgumentException ex) {
            return false;
        }
    }

}
