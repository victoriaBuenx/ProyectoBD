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
/**
 *
 * @author victo
 */
public class PacientesDao {
    
    public boolean insertarPaciente(String nombre, String apellidoPaterno, String apellidoMaterno,
                                    String fechaNacimiento, String telefono, String correo, String fechaRegistro) {
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
            ps.setString(4, fechaNacimiento);
            ps.setString(5, telefono);
            ps.setString(6, correo);
            ps.setString(7, fechaRegistro);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Paciente registrado con éxito.");
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
                if (ps != null) {
                    ps.close();  
                }
                if (con != null) {
                    con.close();  
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar los recursos: " + e.getMessage());
            }
        }
    }
    
}
