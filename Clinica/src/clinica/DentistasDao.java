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
public class DentistasDao {
    Inicio cbxDentistas= new Inicio();
    
        public boolean insertarDentista(String nombre, String apellidoPaterno, String apellidoMaterno,
                                    String especialidad, String telefono, String correo, String horarioAtencion) {
            String sql = "INSERT INTO Dentistas (Nombre, ApellidoPaterno, ApellidoMaterno, Especialidad, Telefono, Correo, HorarioAtencion) " +
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
                ps.setString(4, especialidad);
                ps.setString(5, telefono);
                ps.setString(6, correo);
                ps.setString(7, horarioAtencion);

                int filasAfectadas = ps.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Dentista registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cbxDentistas.llenarComboBox();
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo registrar el dentista.",  "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al insertar dentista: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return false;
            } 
        }
    
        
        public boolean actualizarDentista(int idDentista, String nombre, String apellidoPaterno, String apellidoMaterno, 
                                   String especialidad, String telefono, String correo, String horarioAtencion) {
            String sql = "UPDATE Dentistas SET Nombre = ?, ApellidoPaterno = ?, ApellidoMaterno = ?, Especialidad = ?, "
                       + "Telefono = ?, Correo = ?, HorarioAtencion = ? WHERE idDentista = ?";

            try (Connection con = new Conexion().conexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, nombre);
                ps.setString(2, apellidoPaterno);
                ps.setString(3, apellidoMaterno);
                ps.setString(4, especialidad);
                ps.setString(5, telefono);
                ps.setString(6, correo);
                ps.setString(7, horarioAtencion);
                ps.setInt(8, idDentista);

                int filasAfectadas = ps.executeUpdate();
                 if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Dentista actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo actualizar el dentista.",  "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al actualizar dentista: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        
        public boolean eliminarDentista(int idDentista) {
            String sql = "DELETE FROM Dentistas WHERE idDentista = ?";

            try (Connection con = new Conexion().conexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, idDentista);

                int filasAfectadas = ps.executeUpdate();
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Dentista eliminado con éxito." ,"Éxito", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el dentista.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar dentista: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }



    
}
