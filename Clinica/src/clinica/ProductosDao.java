/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author victo
 */
public class ProductosDao {
    
    public int insertarProductos(String nombreProducto, String descripcion, int precioUnitario) {
        String sql = "INSERT INTO Productos (NombreProducto, Descripcion, PrecioUnitario) VALUES (?, ?, ?)";
        int idProductoGenerado = -1;
        
        try (Connection con = new Conexion().conexion();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nombreProducto);
            ps.setString(2, descripcion);
            ps.setInt(3, precioUnitario);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idProductoGenerado = rs.getInt(1); 
                }
                    JOptionPane.showMessageDialog(null, "Producto registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo registrar el Producto.",  "Error", JOptionPane.ERROR_MESSAGE);
                }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar Producto: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        return idProductoGenerado;
    }
    
    public boolean actualizarProducto(int idProducto, String nombreProducto, String descripcion, int precioUnitario) {
        String sql = "UPDATE Productos SET NombreProducto = ?, Descripcion = ?, PrecioUnitario = ? WHERE idProducto = ?";

        PreparedStatement ps = null;
        Connection con = null;

        try {
            Conexion conexion = new Conexion();
            con = conexion.conexion();

            ps = con.prepareStatement(sql);
            ps.setString(1, nombreProducto);
            ps.setString(2, descripcion);
            ps.setInt(3, precioUnitario);
            ps.setInt(4, idProducto);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Producto actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el producto: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminarProducto(int idProducto) {
        String sql = "DELETE FROM Productos WHERE idProducto = ?";

        PreparedStatement ps = null;
        Connection con = null;

        try {
            Conexion conexion = new Conexion();
            con = conexion.conexion();

            ps = con.prepareStatement(sql);
            ps.setInt(1, idProducto);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Producto eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        } 
    }   
}
