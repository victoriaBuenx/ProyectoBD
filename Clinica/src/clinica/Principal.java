/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package clinica;

import clinica.Conexion;
import java.sql.Connection;

/**
 *
 * @author victo
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Conexion conexionBD = new Conexion();

        Connection conn = conexionBD.conexion(); // Intentar conectar

        if (conn != null) {
            System.out.println("Conectado a la base de datos.");
        } else {
            System.out.println("No se pudo conectar a la base de datos.");
        }

        conexionBD.cerrarConexion();
       
    }
    
}
