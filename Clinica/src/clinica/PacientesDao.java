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
    
    private Connection con = null;

    
        public Connection conexion() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinicadental?useSSL=false&serverTimezone=UTC", "root", "mamapapa41");
                System.out.println("✅ Conexión realizada con éxito");
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Error: No se encontró el driver JDBC.");
                e.printStackTrace();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + e.getMessage());
                e.printStackTrace();
            }
            return con;
        }

       
        public void cerrarConexion() {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("Conexión cerrada correctamente");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage());
            }
        }

        public boolean insertarPaciente(String nombre, String apellidoPaterno, String apellidoMaterno,
                                     String fechaNacimiento, String telefono, String correo, String fechaRegistro) {
         String sql = "INSERT INTO Pacientes (Nombre, ApellidoPaterno, ApellidoMaterno, FechaNacimiento, Telefono, Correo, FechaRegistro) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?)";

         PreparedStatement ps = null;

         try {
             con = conexion();  

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
             } catch (SQLException e) {
                 JOptionPane.showMessageDialog(null, "Error al cerrar el PreparedStatement: " + e.getMessage());
             }
         }
     }
    
}
