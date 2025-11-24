package controller;

import java.util.LinkedList;
import java.util.Queue;

public class RegistrarReserva {
    private Queue<Reserva> colaReservas;

    public RegistrarReserva() {
        colaReservas = new LinkedList<>();
    }

    // Agregar reserva a la cola
    public void agregarReserva(Reserva r) {
        colaReservas.offer(r);
        System.out.println("Reserva agregada a la cola: " + r.getIdReserva());
    }

    // Registrar la primera reserva de la cola (FIFO)
    public Reserva registrarSiguiente() {
        Reserva r = colaReservas.poll();
        if(r != null) {
            System.out.println("Registrando reserva en BD: " + r.getIdReserva());
            // Aquí iría el código de inserción en MySQL
        }
        return r;
    }

    public boolean hayReservas() {
        return !colaReservas.isEmpty();
    }
}
