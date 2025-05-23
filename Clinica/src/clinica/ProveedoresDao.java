/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;



import java.sql.*;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
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

            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Proveedor registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar proveedor", "Error SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
     
     public boolean actualizarProveedor(int idProveedor, String nombre, String telefono, String correo, String empresa, String direccion) {
        String sql = "UPDATE Proveedores SET Nombre = ?, Telefono = ?, Correo = ?, Empresa = ?, Direccion = ? WHERE idProveedor = ?";

        try (Connection con = new Conexion().conexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, telefono);
            ps.setString(3, correo);
            ps.setString(4, empresa);
            ps.setString(5, direccion);
            ps.setInt(6, idProveedor);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Proveedor actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar proveedor", "Error SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
     
    public boolean eliminarProveedor(int idProveedor) {
        String sql = "DELETE FROM Proveedores WHERE idProveedor = ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProveedor);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Proveedor eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar proveedor", "Error SQL", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } 
    
    public void buscarProveedores(String textoBusqueda, JTable tabla) {
    String sql = "SELECT * FROM Proveedores WHERE CONCAT(idProveedor, ' ', Nombre, ' ', Telefono, ' ', Correo, ' ', Empresa, ' ', Direccion) LIKE ?";

    try (Connection con = new Conexion().conexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + textoBusqueda + "%");
            ResultSet rs = ps.executeQuery();

            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{
                "id", "Nombre", "Telefono", "Correo", "Empresa", "Direccion"
            });

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("idProveedor"),
                    rs.getString("Nombre"),
                    rs.getString("Telefono"),
                    rs.getString("Correo"),
                    rs.getString("Empresa"),
                    rs.getString("Direccion")
                });
            }

            tabla.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar proveedores", "Error SQL", JOptionPane.ERROR_MESSAGE);
            //e.printStackTrace();
        }
    }
}
