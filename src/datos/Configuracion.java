/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author José
 */
public class Configuracion {

    private final Properties propiedades = new Properties();
    private OutputStream salida = null;
    private InputStream entrada = null;
    private final String ruta;

    public Configuracion() {
        ruta ="recursos/data.config";
        File file= new File(ruta);
        file = new File(file.getParent());
        if(!file.exists()){            
            boolean j=file.mkdirs();            
            System.out.println(j);
        }
    }
    

    public void insertar(String propiedad, String valor) {
        try {
            salida = new FileOutputStream("recursos/data.config");
            propiedades.setProperty(propiedad, valor);           
            try {
                propiedades.store(salida, null);
            } catch (IOException ex) {
                System.out.println(ruta);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String accderPorpiedades(String propiedad) {
        String valor = null;
        try {
            entrada = new FileInputStream("recursos/data.config");
            propiedades.load(entrada);
            valor = (propiedades.getProperty(propiedad));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return valor;
    }

    public Properties getPropiedades() {
        return propiedades;
    }

    public String getRuta() {
        return ruta;
    }

}
