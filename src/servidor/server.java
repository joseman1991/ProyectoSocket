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

/**
 *
 * @author JOSE
 */
public class server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {

            List<Perfiles> per = new ArrayList<>();
            PerfilesDAO pdao = new PerfilesDAO();
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
}
