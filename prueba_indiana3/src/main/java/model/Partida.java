package model;

public class Partida {
    private int numeroPartida;
    private Usuario usuario;
    private int puntos;
    private long tiempoEmpleado;

    public Partida(int numeroPartida, Usuario usuario, int puntos, long tiempoEmpleado) {
        this.numeroPartida = numeroPartida;
        this.usuario = usuario;
        this.puntos = puntos;
        this.tiempoEmpleado = tiempoEmpleado;
    }

    public int getNumeroPartida() {
        return numeroPartida;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public int getPuntos() {
        return puntos;
    }

    public long getTiempoEmpleado() {
        return tiempoEmpleado;
    }

    @Override
    public String toString() {
        return "Partida " + numeroPartida + " - Usuario: " + usuario.getNombre() + ", Puntos: " + puntos + ", Tiempo: " + (tiempoEmpleado / 1000.0) + " segundos";
    }
}
