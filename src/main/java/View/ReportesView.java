package view;

import controller.ConnectionSQL;
import controller.Reportes;
import controller.Reserva;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportesView extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private JPanel panelArbol;
    private Reportes reportes; // Árbol binario de reservas

    public ReportesView() {
        reportes = new Reportes();
        initComponents();
        setLocationRelativeTo(null);
        cargarReportes();      // Cargar tabla desde la BD
        cargarArbol();         // Cargar árbol por ID de cliente
    }

    private void initComponents() {
        setTitle("Reporte de Reservas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior con tabla
        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{
                "ID Reserva", "Fecha", "Hora", "Turno", "ID Cliente",
                "Mesa", "Personas", "Estado"
        });
        tabla = new JTable(modelo);
        tabla.setFillsViewportHeight(true);
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setPreferredSize(new Dimension(900, 300));
        add(scrollTabla, BorderLayout.NORTH);

        // Panel inferior para el árbol
        panelArbol = new JPanel(new BorderLayout());
        add(panelArbol, BorderLayout.CENTER);
    }

    private void cargarReportes() {
        String sql = "SELECT * FROM reserva ORDER BY fecha ASC, hora ASC";

        try (Connection con = ConnectionSQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            modelo.setRowCount(0); // Limpiar tabla
            reportes = new Reportes(); // Reiniciar árbol

            while (rs.next()) {
                // Crear objeto Reserva
                Reserva r = new Reserva(
                        rs.getString("idReserva"),
                        rs.getDate("fecha"),
                        rs.getTime("hora").toString(),
                        rs.getString("turno"),
                        rs.getInt("idCliente"),
                        rs.getInt("numeroMesa"),
                        rs.getInt("cantidadPersonas"),
                        rs.getString("estado")
                );

                // Agregar a la tabla
                Object[] fila = new Object[]{
                        r.getIdReserva(),
                        r.getFecha(),
                        r.getHora(),
                        r.getTurno(),
                        r.getIdCliente(),
                        r.getNumeroMesa(),
                        r.getCantidadPersonas(),
                        r.getEstado()
                };
                modelo.addRow(fila);

                // Agregar al árbol
                reportes.agregarReserva(r);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar reportes:\n" + ex.getMessage(),
                    "Error BD",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarArbol() {
        DefaultMutableTreeNode raizVisual = new DefaultMutableTreeNode("Reservas por Cliente");
        llenarArbolPorCliente(raizVisual, reportes.getRaiz());

        JTree arbol = new JTree(raizVisual);
        JScrollPane scrollArbol = new JScrollPane(arbol);
        panelArbol.add(scrollArbol, BorderLayout.CENTER);
        panelArbol.revalidate();
    }

    // Llenar árbol agrupando por ID Cliente
    private void llenarArbolPorCliente(DefaultMutableTreeNode raizVisual, Reportes.NodoReserva nodo) {
        if (nodo != null) {
            llenarArbolPorCliente(raizVisual, nodo.izquierda);

            // Buscar o crear nodo del cliente
            String idCliente = String.valueOf(nodo.reserva.getIdCliente());
            DefaultMutableTreeNode nodoCliente = null;

            for (int i = 0; i < raizVisual.getChildCount(); i++) {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) raizVisual.getChildAt(i);
                if (child.getUserObject().equals("Cliente " + idCliente)) {
                    nodoCliente = child;
                    break;
                }
            }

            if (nodoCliente == null) {
                nodoCliente = new DefaultMutableTreeNode("Cliente " + idCliente);
                raizVisual.add(nodoCliente);
            }

            // Agregar reserva como hijo del cliente
            String textoReserva = nodo.reserva.getIdReserva() + " | " + nodo.reserva.getFecha();
            nodoCliente.add(new DefaultMutableTreeNode(textoReserva));

            llenarArbolPorCliente(raizVisual, nodo.derecha);
        }
    }
}
