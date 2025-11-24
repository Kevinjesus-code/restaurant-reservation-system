package controller;

public class PruebaMysql {

    /**
@param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ConnectionSQL dbc=new ConnectionSQL();
        dbc.conectar();
    }
    
}