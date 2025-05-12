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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
            JOptionPane.showMessageDialog(null, "Error al actualizar el producto", "Error SQL", JOptionPane.ERROR_MESSAGE);
            //e.printStackTrace();
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
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto", "Error SQL", JOptionPane.ERROR_MESSAGE);
            //e.printStackTrace();
            return false;
        } 
    }   
    
    public boolean registrarProductoConProveedor(String nombreProducto, String descripcion, int precioUnitario, int idProveedor, int cantidadDisponible, Date fechaRegistro) {
        Connection con = null;
        PreparedStatement psProducto = null;
        PreparedStatement psProveedorProducto = null;
        ResultSet generatedKeys = null;

        try {
            Conexion conexion = new Conexion();
            con = conexion.conexion();
            con.setAutoCommit(false); // Inicia la transacción

            // Insertar producto
            String sqlProducto = "INSERT INTO productos (NombreProducto, Descripcion, PrecioUnitario) VALUES (?, ?, ?)";
            psProducto = con.prepareStatement(sqlProducto, Statement.RETURN_GENERATED_KEYS);
            psProducto.setString(1, nombreProducto);
            psProducto.setString(2, descripcion);
            psProducto.setInt(3, precioUnitario);

            int filasProducto = psProducto.executeUpdate();

            if (filasProducto == 0) {
                con.rollback();
                return false;
            }

            generatedKeys = psProducto.getGeneratedKeys();
            if (!generatedKeys.next()) {
                con.rollback();
                return false;
            }

            int idProducto = generatedKeys.getInt(1);

            // Insertar en proveedorproducto
            String sqlProveedorProducto = "INSERT INTO proveedorproducto (idProveedor, idProducto, PrecioTotal, CantidadDisponible, FechaRegistro) VALUES (?, ?, ?, ?, ?)";
            psProveedorProducto = con.prepareStatement(sqlProveedorProducto);
            int precioTotal = precioUnitario * cantidadDisponible;

            psProveedorProducto.setInt(1, idProveedor);
            psProveedorProducto.setInt(2, idProducto);
            psProveedorProducto.setInt(3, precioTotal);
            psProveedorProducto.setInt(4, cantidadDisponible);
            psProveedorProducto.setDate(5, new java.sql.Date(fechaRegistro.getTime()));

            int filasProveedorProducto = psProveedorProducto.executeUpdate();

            if (filasProveedorProducto == 0) {
                con.rollback();
                return false;
            }

            con.commit(); // Confirmar la transacción
            return true;

        } catch (SQLException e) {
            try {
                if (con != null) con.rollback(); // Reversar cambios si ocurre error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (psProducto != null) psProducto.close();
                if (psProveedorProducto != null) psProveedorProducto.close();
                if (con != null) con.setAutoCommit(true);
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean actualizarProductoConProveedor(int idProducto, String nombreProducto, String descripcion, int precioUnitario, 
            int idProveedor, int cantidadDisponible, Date fechaRegistro) {
        Connection con = null;
        PreparedStatement psProducto = null;
        PreparedStatement psProveedorProducto = null;

        try {
            Conexion conexion = new Conexion();
            con = conexion.conexion();
            con.setAutoCommit(false); // Inicia la transacción

            // Actualizar producto
            String sqlProducto = "UPDATE productos SET NombreProducto = ?, Descripcion = ?, PrecioUnitario = ? WHERE idProducto = ?";
            psProducto = con.prepareStatement(sqlProducto);
            psProducto.setString(1, nombreProducto);
            psProducto.setString(2, descripcion);
            psProducto.setInt(3, precioUnitario);
            psProducto.setInt(4, idProducto);

            int filasProducto = psProducto.executeUpdate();
            if (filasProducto == 0) {
                con.rollback();
                return false;
            }

            // Calcular precio total
            int precioTotal = precioUnitario * cantidadDisponible;

            // Actualizar proveedor-producto
            String sqlProveedorProducto = "UPDATE proveedorproducto SET idProveedor = ?, PrecioTotal = ?, CantidadDisponible = ?, FechaRegistro = ? WHERE idProducto = ?";
            psProveedorProducto = con.prepareStatement(sqlProveedorProducto);
            psProveedorProducto.setInt(1, idProveedor);
            psProveedorProducto.setInt(2, precioTotal);
            psProveedorProducto.setInt(3, cantidadDisponible);
            psProveedorProducto.setDate(4, new java.sql.Date(fechaRegistro.getTime()));
            psProveedorProducto.setInt(5, idProducto);

            int filasProveedorProducto = psProveedorProducto.executeUpdate();
            if (filasProveedorProducto == 0) {
                con.rollback();
                return false;
            }

            con.commit(); // Confirmar transacción
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
                if (psProducto != null) psProducto.close();
                if (psProveedorProducto != null) psProveedorProducto.close();
                if (con != null) con.setAutoCommit(true);
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void buscarProductos(String textoBusqueda, JTable tabla) {
        String sql = "SELECT p.idProducto, " +
                     "CONCAT(pr.idProveedor, ' - ', pr.Nombre) AS Proveedor," +
                     "p.NombreProducto, p.Descripcion, p.PrecioUnitario, " +
                     "pp.CantidadDisponible, pp.PrecioTotal, pp.FechaRegistro " +
                     "FROM Productos p " +
                     "INNER JOIN ProveedorProducto pp ON p.idProducto = pp.idProducto " +
                     "INNER JOIN Proveedores pr ON pp.idProveedor = pr.idProveedor" +
                     "WHERE CONCAT(p.idProducto, ' ', pr.Nombre, ' ', p.NombreProducto, ' ', p.Descripcion, ' ', p.PrecioUnitario, ' ', pp.CantidadDisponible, ' ', pp.PrecioTotal, ' ', pp.FechaRegistro) LIKE ?";

        try (Connection con = new Conexion().conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + textoBusqueda + "%");
            ResultSet rs = ps.executeQuery();

            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{
                "id", "Proveedor", "NombreProducto", "Descripcion", "PrecioUnitario", "CantidadDisponible", "PrecioTotal", "FechaRegistro"
            });

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("idProducto"),
                    rs.getString("Proveedor"),
                    rs.getString("NombreProducto"),
                    rs.getString("Descripcion"),
                    rs.getDouble("PrecioUnitario"),
                    rs.getInt("CantidadDisponible"),
                    rs.getDouble("PrecioTotal"),
                    rs.getDate("FechaRegistro")
                });
            }

            tabla.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar productos", "Error SQL", JOptionPane.ERROR_MESSAGE);
            //e.printStackTrace();
        }
    }   
}
