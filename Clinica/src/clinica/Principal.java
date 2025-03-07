/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package clinicadental;

import clinica.Conexion;
import com.sun.jdi.connect.spi.Connection;

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
        Conexion con= new Conexion();
        Connection reg= (Connection) con.conexion();
       
    }
    
}
