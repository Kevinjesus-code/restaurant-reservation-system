package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQL {
    // CORREGIDO: Agregado el prefijo jdbc:mysql://
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_reservas";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection conexion = null;
   
    public static Connection conectar() {
        try {
            if (conexion == null || conexion.isClosed()) {
                // Cargar el driver de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Establecer conexión
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión establecida con la base de datos.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver JDBC de MySQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        return conexion;
    }
    
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}