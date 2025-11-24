package controller;
import java.sql.*;

public class ConnectionSQL {
   static String url="jdbc:mysql://localhost:3306/sistemas_reservas";
    static String user="root";
   static String pass="";
    
    public static Connection conectar()
    {
       Connection con=null;
       try
       {
       con=DriverManager.getConnection(url,user,pass);
           System.out.println("Conexión exitosa");
       }catch(SQLException e)
       {
        e.printStackTrace();
       }
       
       return con;
               
    }
    
}