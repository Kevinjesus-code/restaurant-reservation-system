package view;

import controller.ConnectionSQL;
import controller.RegistrarReserva;
import controller.Reserva;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrarReservaView extends JFrame {

    private JTextField txtClienteID, txtMesa, txtCantidadPersonas, txtTurno;
    private JButton btnAgregar;
    private RegistrarReserva colaReservas;

    public RegistrarReservaView() {
        colaReservas = new RegistrarReserva();
        initComponents();
        setLocationRelativeTo(null);
    }

   private void initComponents() {
    setTitle("Registrar Reserva");
    setSize(450, 450);
    setLayout(new GridBagLayout());
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Labels
    JLabel lblClienteID = new JLabel("ID Cliente:");
    JLabel lblMesa = new JLabel("Número de Mesa:");
    JLabel lblCantidad = new JLabel("Cantidad de Personas:");
    JLabel lblTurno = new JLabel("Turno (DIA/NOCHE):");

    // Campos de texto más grandes
    txtClienteID = new JTextField();
    txtClienteID.setPreferredSize(new Dimension(300, 40));
    txtMesa = new JTextField();
    txtMesa.setPreferredSize(new Dimension(300, 40));
    txtCantidadPersonas = new JTextField();
    txtCantidadPersonas.setPreferredSize(new Dimension(300, 40));
    txtTurno = new JTextField();
    txtTurno.setPreferredSize(new Dimension(300, 40));

    // Botón
    btnAgregar = new JButton("Agregar Reserva");
    btnAgregar.setBackground(new Color(46, 204, 113));
    btnAgregar.setForeground(Color.BLACK);
    btnAgregar.setPreferredSize(new Dimension(300, 50));

    // Fila 0: ID Cliente
    gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
    add(lblClienteID, gbc);
    gbc.gridx = 1; gbc.weightx = 1.0;
    add(txtClienteID, gbc);

    // Fila 1: Número de Mesa
    gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
    add(lblMesa, gbc);
    gbc.gridx = 1; gbc.weightx = 1.0;
    add(txtMesa, gbc);

    // Fila 2: Cantidad de Personas
    gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
    add(lblCantidad, gbc);
    gbc.gridx = 1; gbc.weightx = 1.0;
    add(txtCantidadPersonas, gbc);

    // Fila 3: Turno
    gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
    add(lblTurno, gbc);
    gbc.gridx = 1; gbc.weightx = 1.0;
    add(txtTurno, gbc);

    // Fila 4: Botón
    gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
    gbc.weightx = 0;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.CENTER;
    add(btnAgregar, gbc);

    // Acción del botón
    btnAgregar.addActionListener(e -> agregarReserva());
}


   private void agregarReserva() {
    try {
        int idCliente = Integer.parseInt(txtClienteID.getText().trim());
        int numeroMesa = Integer.parseInt(txtMesa.getText().trim());
        int cantidadPersonas = Integer.parseInt(txtCantidadPersonas.getText().trim());
        String turno = txtTurno.getText().trim().toUpperCase();

        if (!turno.equals("DIA") && !turno.equals("NOCHE")) {
            JOptionPane.showMessageDialog(this, "Turno debe ser DIA o NOCHE", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Fecha y hora actuales
        Date fechaActual = new Date();
        String horaActual = new SimpleDateFormat("HH:mm:ss").format(new Date());

        // Generar ID
        String idReserva = "RES-" + System.currentTimeMillis();

        // ✔ Constructor correcto con todos los parámetros
        Reserva nuevaReserva = new Reserva(
                idReserva,
                fechaActual,
                horaActual,
                turno,
                idCliente,
                numeroMesa,
                cantidadPersonas,
                "ACTIVA"
        );

        // Agregar a cola
        colaReservas.agregarReserva(nuevaReserva);

        // Guardar BD
        guardarEnBD(nuevaReserva);

        JOptionPane.showMessageDialog(this, "Reserva agregada con éxito.\nID: " + idReserva);
        limpiarCampos();

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Ingrese valores válidos en los campos numéricos", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos:\n" + ex.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
    }
}


    private void guardarEnBD(Reserva r) throws SQLException {
        String sql = "INSERT INTO reserva (idReserva, fecha, hora, turno, idCliente, numeroMesa, cantidadPersonas, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionSQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, r.getIdReserva());
            java.sql.Date sqlFecha = new java.sql.Date(r.getFecha().getTime());
            ps.setDate(2, sqlFecha);
            java.sql.Time sqlHora = new java.sql.Time(r.getFecha().getTime());
            ps.setTime(3, sqlHora);
            ps.setString(4, r.getTurno());
            ps.setInt(5, r.getIdCliente());
            ps.setInt(6, r.getNumeroMesa());
            ps.setInt(7, r.getCantidadPersonas());
            ps.setString(8, r.getEstado());

            ps.executeUpdate();
        }
    }

    private void limpiarCampos() {
        txtClienteID.setText("");
        txtMesa.setText("");
        txtCantidadPersonas.setText("");
        txtTurno.setText("");
    }
}
