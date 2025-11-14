package app;

import view.LoginView;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Establecer Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Iniciar la aplicación con el LoginView
        EventQueue.invokeLater(() -> {
            LoginView login = new LoginView();
            login.setVisible(true);
        });
    }
}