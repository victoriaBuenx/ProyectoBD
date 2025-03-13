/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.ResultSet;


/**
 *
 * @author victo
 */
public class TratamientosDao {
    Inicio cbxTratamientos= new Inicio();
    
        public int insertarTratamiento(int idDentista, String nombre, String descripcion, int montoTotal) {
        String sql = "INSERT INTO Tratamientos (idDentista, Nombre, Descripcion, MontoTotal) VALUES (?, ?, ?, ?)";
        int idTratamientoGenerado = -1;  // Valor por defecto en caso de error

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idDentista);
            ps.setString(2, nombre);
            ps.setString(3, descripcion);
            ps.setInt(4, montoTotal);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                // Obtener el idTratamiento generado
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idTratamientoGenerado = rs.getInt(1);  // El primer valor de la fila es el id generado
                }
                JOptionPane.showMessageDialog(null, "Tratamiento registrado con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar el tratamiento.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar tratamiento: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return idTratamientoGenerado;
    }
    
    public boolean actualizarTratamiento(int idTratamiento, int idDentista, String nombre, String descripcion, int montoTotal) {
        String sql = "UPDATE Tratamientos SET idDentista = ?, Nombre = ?, Descripcion = ?, MontoTotal = ? WHERE idTratamiento = ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idDentista);
            ps.setString(2, nombre);
            ps.setString(3, descripcion);
            ps.setInt(4, montoTotal);
            ps.setInt(5, idTratamiento);

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar tratamiento: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean eliminarTratamiento(int idTratamiento) {
        String sql = "DELETE FROM Tratamientos WHERE idTratamiento = ?";
        PreparedStatement ps = null;
        Connection con = null;

        try {
            Conexion conexion = new Conexion();
            con = conexion.conexion();

            ps = con.prepareStatement(sql);
            ps.setInt(1, idTratamiento);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Tratamiento eliminado con éxito.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el tratamiento.");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar tratamiento: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
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
