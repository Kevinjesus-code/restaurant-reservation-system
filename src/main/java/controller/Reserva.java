package controller;

import java.util.Date;

public class Reserva {
    // Datos de la reserva
    private String idReserva;
    private Date fecha;
    private String hora; // Puedes usar String o Time según tu BD
    private String turno;
    private int idCliente;
    private int numeroMesa;
    private int cantidadPersonas;
    private String estado;

    // Datos del cliente
    private String nombreCliente;
    private String dni;
    private String telefono;
    private String correo;
    private boolean isVIP;
    

    // Constructor para reserva básica
    public Reserva(String idReserva, Date fecha, String hora, String turno, int idCliente, int numeroMesa, int cantidadPersonas, String estado) {
        this.idReserva = idReserva;
        this.fecha = fecha;
        this.hora = hora;
        this.turno = turno;
        this.idCliente = idCliente;
        this.numeroMesa = numeroMesa;
        this.cantidadPersonas = cantidadPersonas;
        this.estado = estado;
    }

    public Reserva() {
        // Constructor vacío para cargar desde BD
    }

    // Getters y Setters de reserva
    public String getIdReserva() { return idReserva; }
    public void setIdReserva(String idReserva) { this.idReserva = idReserva; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getNumeroMesa() { return numeroMesa; }
    public void setNumeroMesa(int numeroMesa) { this.numeroMesa = numeroMesa; }

    public int getCantidadPersonas() { return cantidadPersonas; }
    public void setCantidadPersonas(int cantidadPersonas) { this.cantidadPersonas = cantidadPersonas; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    // Getters y Setters del cliente
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public boolean isVIP() { return isVIP; }
    public void setVIP(boolean VIP) { isVIP = VIP; }

    @Override
    public String toString() {
        return "Reserva[" + idReserva + ", Fecha=" + fecha + ", Hora=" + hora + ", Turno=" + turno +
               ", ClienteID=" + idCliente + ", Mesa=" + numeroMesa + ", Personas=" + cantidadPersonas +
               ", Estado=" + estado + ", Cliente=" + nombreCliente + "]";
    }
}
