package view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class PanelPrincipal extends JFrame {

    private JButton btnRegistrar, btnVerificar, btnReportes;
    private JLabel lblBienvenida;
    private String nombreUsuario;

    public PanelPrincipal(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Panel principal con gradiente suave
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 247, 255), 0, getHeight(), new Color(255, 255, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridBagLayout());

        // Panel contenedor central
        JPanel containerPanel = new JPanel();
        containerPanel.setOpaque(false);
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.setBorder(new EmptyBorder(30, 50, 30, 50));

        // Panel de encabezado
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Título principal
        JLabel lblTitulo = new JLabel("Sistema de Reservas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(new Color(74, 144, 226));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Mensaje de bienvenida
        lblBienvenida = new JLabel("Bienvenido, " + nombreUsuario);
        lblBienvenida.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblBienvenida.setForeground(new Color(100, 100, 100));
        lblBienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(lblTitulo);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(lblBienvenida);

        // Panel de botones con diseño de tarjetas
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 20));
        buttonPanel.setMaximumSize(new Dimension(450, 400));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crear botones con estilo de tarjeta
        btnRegistrar = crearBotonTarjeta(
            "Registrar Reserva",
            "Crear una nueva reserva en el sistema",
            new Color(46, 204, 113),
            "➕"
        );

        btnVerificar = crearBotonTarjeta(
            "Verificar Reserva",
            "Consultar el estado de una reserva",
            new Color(52, 152, 219),
            "🔍"
        );

        btnReportes = crearBotonTarjeta(
            "Reportes",
            "Generar reportes y estadísticas",
            new Color(155, 89, 182),
            "📊"
        );

        // Agregar ActionListeners - Aquí agregarás tus funciones
        btnRegistrar.addActionListener(e -> {
            // TODO: Abrir ventana de registro de reservas
            JOptionPane.showMessageDialog(this, "Función: Registrar Reserva", "En desarrollo", JOptionPane.INFORMATION_MESSAGE);
        });

        btnVerificar.addActionListener(e -> {
            // TODO: Abrir ventana de verificación de reservas
            JOptionPane.showMessageDialog(this, "Función: Verificar Reserva", "En desarrollo", JOptionPane.INFORMATION_MESSAGE);
        });

        btnReportes.addActionListener(e -> {
            // TODO: Abrir ventana de reportes
            JOptionPane.showMessageDialog(this, "Función: Reportes", "En desarrollo", JOptionPane.INFORMATION_MESSAGE);
        });

        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnVerificar);
        buttonPanel.add(btnReportes);

        // Agregar componentes al contenedor
        containerPanel.add(headerPanel);
        containerPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        containerPanel.add(buttonPanel);

        // Agregar contenedor al panel principal
        GridBagConstraints gbc = new GridBagConstraints();
        mainPanel.add(containerPanel, gbc);

        // Configuración de la ventana
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Panel Principal - Sistema de Reservas");
        setContentPane(mainPanel);
        setSize(700, 650);
        setMinimumSize(new Dimension(600, 600));
    }

    private JButton crearBotonTarjeta(String titulo, String descripcion, Color colorPrincipal, String icono) {
        JButton boton = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Sombra
                g2d.setColor(new Color(0, 0, 0, 20));
                g2d.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 15, 15);

                // Fondo
                if (getModel().isPressed()) {
                    g2d.setColor(Color.WHITE);
                } else if (getModel().isRollover()) {
                    GradientPaint gp = new GradientPaint(0, 0, Color.WHITE, 0, getHeight(), new Color(250, 250, 255));
                    g2d.setPaint(gp);
                } else {
                    g2d.setColor(Color.WHITE);
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Borde
                g2d.setColor(new Color(230, 230, 230));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

                // Línea de color
                g2d.setColor(colorPrincipal);
                g2d.setStroke(new BasicStroke(4));
                g2d.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 12, 12);

                g2d.dispose();
            }
        };

        boton.setLayout(new BorderLayout(15, 0));
        boton.setPreferredSize(new Dimension(400, 100));
        boton.setMaximumSize(new Dimension(450, 100));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Panel izquierdo con icono
        JPanel iconPanel = new JPanel();
        iconPanel.setOpaque(false);
        iconPanel.setPreferredSize(new Dimension(80, 100));
        iconPanel.setLayout(new GridBagLayout());

        JLabel lblIcono = new JLabel(icono);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        iconPanel.add(lblIcono);

        // Panel derecho con texto
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBorder(new EmptyBorder(20, 0, 20, 20));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(60, 60, 60));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDescripcion = new JLabel(descripcion);
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDescripcion.setForeground(new Color(120, 120, 120));
        lblDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(lblTitulo);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        textPanel.add(lblDescripcion);

        boton.add(iconPanel, BorderLayout.WEST);
        boton.add(textPanel, BorderLayout.CENTER);

        // Efecto hover mejorado
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                boton.repaint();
            }
            public void mouseExited(MouseEvent e) {
                boton.repaint();
            }
        });

        return boton;
    }
}