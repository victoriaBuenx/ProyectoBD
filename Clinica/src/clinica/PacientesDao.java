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
public class PacientesDao {
    Inicio cbxPacientes= new Inicio();
    
    public boolean insertarPaciente(String nombre, String apellidoPaterno, String apellidoMaterno,
                                    Date fechaNacimiento, String telefono, String correo, Date fechaRegistro) {
        String sql = "INSERT INTO Pacientes (Nombre, ApellidoPaterno, ApellidoMaterno, FechaNacimiento, Telefono, Correo, FechaRegistro) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = null;
        Connection con = null;  

        try {
            Conexion conexion = new Conexion();  
            con = conexion.conexion();  

            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellidoPaterno);
            ps.setString(3, apellidoMaterno);
            ps.setDate(4, new java.sql.Date(fechaNacimiento.getTime())); 
            ps.setString(5, telefono);
            ps.setString(6, correo);
            ps.setDate(7, new java.sql.Date(fechaRegistro.getTime())); 

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Paciente registrado con éxito.");
                cbxPacientes.llenarComboBox();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar el paciente.");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar paciente: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar los recursos: " + e.getMessage());
            }
        }
    }
    
}
