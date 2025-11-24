package controller;

public class Reportes {
    private NodoReserva raiz;

    public Reportes() {
        raiz = null;
    }

    // Insertar reserva en el árbol (ordenado por fecha)
    public void agregarReserva(Reserva r) {
        raiz = insertar(raiz, r);
    }

    private NodoReserva insertar(NodoReserva nodo, Reserva r) {
        if (nodo == null) return new NodoReserva(r);

        if (r.getFecha().compareTo(nodo.reserva.getFecha()) < 0) {
            nodo.izquierda = insertar(nodo.izquierda, r);
        } else {
            nodo.derecha = insertar(nodo.derecha, r);
        }
        return nodo;
    }

    // Recorrido in-order para mostrar reportes en consola
    public void mostrarReportes() {
        System.out.println("=== Reporte de Reservas ===");
        inOrder(raiz);
    }

    private void inOrder(NodoReserva nodo) {
        if (nodo != null) {
            inOrder(nodo.izquierda);
            System.out.println(nodo.reserva);
            inOrder(nodo.derecha);
        }
    }

    // Getter de la raíz para usar en ReportesView
    public NodoReserva getRaiz() {
        return raiz;
    }

    // Clase interna pública y estática para poder acceder desde otras clases
    public static class NodoReserva {
    public Reserva reserva;
    public NodoReserva izquierda, derecha;

    public NodoReserva(Reserva r) {
        this.reserva = r;
        izquierda = derecha = null;
    }
}

}
