package view;

import controller.ConnectionSQL;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.*;

public class LoginView extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginView() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Panel principal simple
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setLayout(new GridBagLayout());

        // Panel de login blanco
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setPreferredSize(new Dimension(350, 400));
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(30, 40, 30, 40)
        ));

        // Título
        JLabel lblTitulo = new JLabel("Sistema de Reservas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(74, 144, 226));

        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Inicia sesión para continuar");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(120, 120, 120));

        // Label Usuario
        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblUsuario.setForeground(new Color(60, 60, 60));

        // Campo Usuario
        txtUsuario = new JTextField(20);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsuario.setPreferredSize(new Dimension(270, 35));
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));

        // Label Contraseña
        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPassword.setForeground(new Color(60, 60, 60));

        // Campo Contraseña
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setPreferredSize(new Dimension(270, 35));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));

        // Botón Login
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(new Color(74, 144, 226));
        btnLogin.setPreferredSize(new Dimension(270, 40));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(52, 122, 204));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(74, 144, 226));
            }
        });

        btnLogin.addActionListener(evt -> btnLoginActionPerformed(evt));

        // Layout del panel de login
        GroupLayout layout = new GroupLayout(loginPanel);
        loginPanel.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(lblTitulo)
                .addComponent(lblSubtitulo)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblUsuario)
                        .addComponent(txtUsuario)
                        .addComponent(lblPassword)
                        .addComponent(txtPassword)
                        .addComponent(btnLogin)))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(10)
                .addComponent(lblTitulo)
                .addGap(5)
                .addComponent(lblSubtitulo)
                .addGap(30)
                .addComponent(lblUsuario)
                .addGap(5)
                .addComponent(txtUsuario, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addGap(15)
                .addComponent(lblPassword)
                .addGap(5)
                .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addGap(25)
                .addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE)
        );

        // Agregar panel de login al panel principal
        GridBagConstraints gbc = new GridBagConstraints();
        mainPanel.add(loginPanel, gbc);

        // Configuración de la ventana
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login - Sistema de Reservas");
        setContentPane(mainPanel);
        setSize(600, 550);
        setMinimumSize(new Dimension(500, 500));
    }

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, ingrese usuario y contraseña.", 
                "Campos vacíos", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = ConnectionSQL.conectar()) {
            String query = "SELECT * FROM empleado WHERE usuario = ? AND contrasena = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, usuario);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nombreEmpleado = rs.getString("nombre");
                JOptionPane.showMessageDialog(this, 
                    "Bienvenido " + nombreEmpleado + "!", 
                    "Acceso exitoso", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Cerrar login y abrir menú principal
                this.dispose();
                new PanelPrincipal(nombreEmpleado).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Usuario o contraseña incorrectos.", 
                    "Error de acceso", 
                    JOptionPane.ERROR_MESSAGE);
                txtPassword.setText("");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al conectar con la base de datos:\n" + e.getMessage(), 
                "Error de conexión", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}