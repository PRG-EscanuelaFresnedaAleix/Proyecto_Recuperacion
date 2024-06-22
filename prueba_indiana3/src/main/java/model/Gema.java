package model;

public class Gema {
    private Coordenada coordenada;
    private int puntuacion;
    private String color;

    public Gema(Coordenada coordenada, int puntuacion, String color) {
        this.coordenada = coordenada;
        this.puntuacion = puntuacion;
        this.color = color;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public String getColor() {
        return color;
    }
}
