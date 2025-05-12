/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author victo
 */
public class ProveedorProductoDao {
    public boolean insertarProveedorProducto (int idProveedor, int idProducto, int CantidadDisponible, Date FechaRegistro) {
        String sql = "INSERT INTO ProveedorProducto (idProveedor, idProducto, CantidadDisponible, FechaRegistro) " +
                     "VALUES (?, ?, ?, ?)";

        PreparedStatement ps = null;
        Connection con = null;  

        try {
            Conexion conexion = new Conexion();  
            con = conexion.conexion();  

            ps = con.prepareStatement(sql);
            ps.setInt(1, idProveedor);
            ps.setInt(2, idProducto);
            ps.setInt(3, CantidadDisponible); 
            ps.setDate(4, new java.sql.Date(FechaRegistro.getTime())); 

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar el ProveedorProducto.");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar proveedorProducto", "Error SQL", JOptionPane.ERROR_MESSAGE);
            //e.printStackTrace();
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
    
    public boolean actualizarProveedorProducto(int idProveedorProducto, int idProveedor, int idProducto, int cantidadDisponible, Date fechaRegistro, int precioTotal) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            Conexion conexion = new Conexion();
            con = conexion.conexion();

            String sql = "UPDATE proveedorproducto SET idProveedor = ?, idProducto = ?, PrecioTotal = ?, CantidadDisponible = ?, FechaRegistro = ? WHERE idProveedorProducto = ?";
            ps = con.prepareStatement(sql);

            ps.setInt(1, idProveedor);
            ps.setInt(2, idProducto);
            ps.setInt(3, precioTotal); // Nuevo campo incluido correctamente
            ps.setInt(4, cantidadDisponible);
            ps.setDate(5, new java.sql.Date(fechaRegistro.getTime()));
            ps.setInt(6, idProveedorProducto);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public boolean eliminarProveedorProducto(int idProveedorProducto) {
        String sql = "DELETE FROM Pacientes WHERE idProveedor = ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProveedorProducto);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "ProveedorProducto eliminado con éxito.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el proveedorProducto.");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar proveedorProducto", "Error SQL", JOptionPane.ERROR_MESSAGE);
            //e.printStackTrace();
            return false;
        }
    }
    
    public int obtenerIdProveedorProducto(int idProveedor, int idProducto) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Conexion conexion = new Conexion();
            con = conexion.conexion();

            String sql = "SELECT idProveedorProducto FROM proveedorproducto WHERE idProveedor = ? AND idProducto = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idProveedor);
            ps.setInt(2, idProducto);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("idProveedorProducto");
            } else {
                return -1; // No encontrado
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean actualizarProductoConProveedor(int idProducto, String nombreProducto, String descripcion, int precioUnitario, 
            int idProveedorProducto, int idProveedor, int cantidadDisponible, Date fechaRegistro) {
        
        Connection con = null;
        PreparedStatement psActualizarProducto = null;
        PreparedStatement psActualizarProveedorProducto = null;

        try {
            Conexion conexion = new Conexion();
            con = conexion.conexion();
            con.setAutoCommit(false); // Iniciar la transacción

            // Actualizar producto
            String sqlProducto = "UPDATE productos SET NombreProducto = ?, Descripcion = ?, PrecioUnitario = ? WHERE idProducto = ?";
            psActualizarProducto = con.prepareStatement(sqlProducto);
            psActualizarProducto.setString(1, nombreProducto);
            psActualizarProducto.setString(2, descripcion);
            psActualizarProducto.setInt(3, precioUnitario);
            psActualizarProducto.setInt(4, idProducto);

            int filasProducto = psActualizarProducto.executeUpdate();
            if (filasProducto == 0) {
                con.rollback();
                return false;
            }

            // Calcular nuevo precio total
            int precioTotal = precioUnitario * cantidadDisponible;

            // Actualizar proveedorproducto
            String sqlProveedorProducto = "UPDATE proveedorproducto SET idProveedor = ?, idProducto = ?, PrecioTotal = ?, CantidadDisponible = ?, FechaRegistro = ? WHERE idProveedorProducto = ?";
            psActualizarProveedorProducto = con.prepareStatement(sqlProveedorProducto);
            psActualizarProveedorProducto.setInt(1, idProveedor);
            psActualizarProveedorProducto.setInt(2, idProducto);
            psActualizarProveedorProducto.setInt(3, precioTotal);
            psActualizarProveedorProducto.setInt(4, cantidadDisponible);
            psActualizarProveedorProducto.setDate(5, new java.sql.Date(fechaRegistro.getTime()));
            psActualizarProveedorProducto.setInt(6, idProveedorProducto);

            int filasProveedorProducto = psActualizarProveedorProducto.executeUpdate();
            if (filasProveedorProducto == 0) {
                con.rollback();
                return false;
            }

            con.commit(); // Confirmar los cambios
            return true;

        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (psActualizarProducto != null) psActualizarProducto.close();
                if (psActualizarProveedorProducto != null) psActualizarProveedorProducto.close();
                if (con != null) con.setAutoCommit(true);
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    
}
