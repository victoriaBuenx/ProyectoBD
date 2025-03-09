/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author victo
 */
public class TratamientosDao {
    Inicio cbxTratamientos= new Inicio();
    public boolean insertarTratamientos(int idDentista, String nombre, String descripcion, int montoTotal) {
        String sql = "INSERT INTO Tratamientos (idDentista, Nombre, Descripcion, MontoTotal) " +
                     "VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        Connection con = null;  

        try {
            Conexion conexion = new Conexion();  
            con = conexion.conexion();  

            ps = con.prepareStatement(sql);
            ps.setInt(1, idDentista);
            ps.setString(2, nombre);
            ps.setString(3, descripcion);
            ps.setInt(4, montoTotal); 

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Tratamiento registrado con éxito.");
                cbxTratamientos.llenarComboBox();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar el tratamiento.");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar tratamiento: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
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
