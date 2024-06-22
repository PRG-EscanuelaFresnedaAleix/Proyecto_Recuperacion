package model;

public class ObjetoVidaExtra {
    private Coordenada coordenada;
    private int movimientosRestantes;

    public ObjetoVidaExtra(Coordenada coordenada, int movimientosRestantes) {
        this.coordenada = coordenada;
        this.movimientosRestantes = movimientosRestantes;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
    }

    public int getMovimientosRestantes() {
        return movimientosRestantes;
    }

    public void decrementarMovimientos() {
        this.movimientosRestantes--;
    }
}
