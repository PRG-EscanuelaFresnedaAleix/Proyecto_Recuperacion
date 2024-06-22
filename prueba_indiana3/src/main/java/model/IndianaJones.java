package model;

public class IndianaJones extends Personaje {
    private boolean proteccion;
    private int vidas;

    public IndianaJones(Coordenada coordenada, int vidas) {
        super(coordenada);
        this.proteccion = false;
        this.vidas = vidas;
    }

    public boolean tieneProteccion() {
        return proteccion;
    }

    public void activarProteccion() {
        this.proteccion = true;
    }

    public void desactivarProteccion() {
        this.proteccion = false;
    }

    public void perderVida() {
        if (this.proteccion) {
            this.desactivarProteccion();
        } else {
            this.vidas--;
        }
    }

    public void perderVidaEnemigoMaligno() {
        if (this.proteccion) {
            this.desactivarProteccion();
        } else {
            this.vidas -= 2;
        }
    }

    public void ganarVida() {
        this.vidas++;
    }

    public int getVidas() {
        return vidas;
    }

    public Coordenada getPosicionInicial() {
        return new Coordenada(1, 1);
    }

    public void resetearPosicion() {
        this.setCoordenada(new Coordenada(1, 1));
    }
}
