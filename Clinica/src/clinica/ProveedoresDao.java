/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;


import clinica.Conexion;

import java.sql.*;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
/**
 *
 * @author victo
 */
public class ProveedoresDao {
    
     public boolean insertarTablaProveedores(String nombre, String telefono, String correo, String empresa, String direccion) {
        String sql = "INSERT INTO Proveedores (Nombre, Telefono, Correo, Empresa, Direccion) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, telefono);
            ps.setString(3, correo);
            ps.setString(4, empresa);
            ps.setString(5, direccion);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar proveedor: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    
}
