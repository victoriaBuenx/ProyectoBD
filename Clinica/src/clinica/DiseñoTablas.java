/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinica;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
 *
 * @author victo
 */
public class DiseñoTablas {
    
    public void personalizarTabla(JTable... tablas) {
        for (JTable tabla : tablas) {
            tabla.setBackground(Color.white);
            tabla.setShowHorizontalLines(true);
            tabla.setShowVerticalLines(true);
            tabla.setRowHeight(30);
            JTableHeader header = tabla.getTableHeader();
            header.setForeground(Color.black);
            header.setPreferredSize(new Dimension(header.getWidth(), (35)));
            header.setFont(new Font("Poppins", Font.PLAIN, 12));
            tabla.getColumnModel().getColumn(0).setPreferredWidth(25);
        }
    }
    public void personalizarRenderizado(JTable... tablas) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    
                if (isSelected) {
                    c.setBackground(new Color (155,196,226));
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
    
                setBorder(BorderFactory.createEmptyBorder()); 
                return c;
            }
        };
    
        for (JTable tabla : tablas) {
            tabla.setDefaultRenderer(Object.class, renderer);
        }
    }
}
