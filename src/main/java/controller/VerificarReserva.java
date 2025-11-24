package controller;

import java.util.Stack;

public class VerificarReserva {
    private Stack<Reserva> pilaReservas;

    public VerificarReserva() {
        pilaReservas = new Stack<>();
    }

    public void agregarReserva(Reserva r) {
        pilaReservas.push(r);
        System.out.println("Reserva agregada a la pila: " + r.getIdReserva());
    }

    public Reserva verificarUltima() {
        if(!pilaReservas.isEmpty()) {
            Reserva r = pilaReservas.pop();
            System.out.println("Verificando reserva: " + r.getIdReserva());
            // Aquí se puede actualizar el estado en la BD
            return r;
        } else {
            System.out.println("No hay reservas para verificar.");
            return null;
        }
    }

    public boolean hayReservas() {
        return !pilaReservas.isEmpty();
    }

    public Iterable<Reserva> obtenerReservasBD() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
