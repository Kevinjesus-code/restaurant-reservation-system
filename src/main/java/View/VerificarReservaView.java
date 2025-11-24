package view;

import controller.ConnectionSQL;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VerificarReservaView extends JFrame {

    private JTextField txtBuscarID;
    private JButton btnBuscar;
    private JTextArea txtResultado;

    public VerificarReservaView() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Verificar Reserva");
        setSize(500, 500);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label
        JLabel lblID = new JLabel("Ingrese ID de Reserva:");

        // Campo de texto
        txtBuscarID = new JTextField();
        txtBuscarID.setPreferredSize(new Dimension(250, 40));

        // Botón buscar
        btnBuscar = new JButton("Buscar Reserva");
        btnBuscar.setPreferredSize(new Dimension(200, 45));
        btnBuscar.setBackground(new Color(52, 152, 219));
        btnBuscar.setForeground(Color.BLACK);

        // Área de resultado
        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(txtResultado);
        scroll.setPreferredSize(new Dimension(400, 250));

        // Fila 0
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblID, gbc);

        gbc.gridx = 1;
        add(txtBuscarID, gbc);

        // Fila 1
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnBuscar, gbc);

        // Fila 2
        gbc.gridy = 2;
        add(scroll, gbc);

        // Acción botón
        btnBuscar.addActionListener(e -> buscarReserva());
    }

    private void buscarReserva() {
        String idReserva = txtBuscarID.getText().trim();

        if (idReserva.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID de reserva.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "SELECT * FROM reserva WHERE idReserva = ?";

        try (Connection con = ConnectionSQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, idReserva);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                StringBuilder sb = new StringBuilder();

                sb.append("=== RESERVA ENCONTRADA ===\n\n");
                sb.append("ID Reserva: ").append(rs.getString("idReserva")).append("\n");
                sb.append("Fecha: ").append(rs.getDate("fecha")).append("\n");
                sb.append("Hora: ").append(rs.getTime("hora")).append("\n");
                sb.append("Turno: ").append(rs.getString("turno")).append("\n");
                sb.append("ID Cliente: ").append(rs.getInt("idCliente")).append("\n");
                sb.append("Mesa: ").append(rs.getInt("numeroMesa")).append("\n");
                sb.append("Personas: ").append(rs.getInt("cantidadPersonas")).append("\n");
                sb.append("Estado: ").append(rs.getString("estado")).append("\n");

                txtResultado.setText(sb.toString());
            } else {
                txtResultado.setText("No se encontró ninguna reserva con el ID especificado.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                    "Error al consultar la base de datos:\n" + ex.getMessage(), 
                    "Error BD", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
