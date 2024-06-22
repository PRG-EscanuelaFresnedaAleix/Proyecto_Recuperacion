package model;

public class Usuario {
    private String nombre;
    private String password;
    private boolean esPremium;
    private int vidas;

    public Usuario(String nombre, String password, boolean esPremium) {
        this.nombre = nombre;
        this.password = password;
        this.esPremium = esPremium;
        this.vidas = esPremium ? 4 : 3;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    public boolean esPremium() {
        return esPremium;
    }

    public int getVidas() {
        return vidas;
    }

    public void perderVida() {
        vidas--;
    }

    public void ganarVida() {
        vidas++;
    }
}
