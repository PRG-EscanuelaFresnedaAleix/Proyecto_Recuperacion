package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Escenario {
    private char[][] matriz;
    private IndianaJones indianaJones;
    private Serpiente[] serpientes;
    private List<Gema> gemas;
    private ObjetoProtector objetoProtector;
    private PersonajeMaligno personajeMaligno;
    private ObjetoVidaExtra objetoVidaExtra;
    private int puntosRecolectados;
    private int puntosMaximos;
    private int contadorMovimientos; 
    private Coordenada coordenadaVidaExtra; 
    private Usuario usuario; 

    public Escenario(Usuario usuario) {
        matriz = new char[10][10];
        serpientes = new Serpiente[3];
        gemas = new ArrayList<>();
        puntosRecolectados = 0;
        puntosMaximos = 0;
        contadorMovimientos = 0; 
        this.usuario = usuario; 
    }

    public void inicializar() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (i == 0 || i == matriz.length - 1 || j == 0 || j == matriz[i].length - 1) {
                    matriz[i][j] = '#';
                } else {
                    matriz[i][j] = '.';
                }
            }
        }

        int vidasIniciales = usuario.esPremium() ? 4 : 3;
        indianaJones = new IndianaJones(new Coordenada(1, 1), vidasIniciales);
        matriz[indianaJones.getCoordenada().getFila()][indianaJones.getCoordenada().getColumna()] = '9';

        Random rand = new Random();
        for (int i = 0; i < serpientes.length; i++) {
            Coordenada coord;
            do {
                coord = new Coordenada(rand.nextInt(8) + 1, rand.nextInt(8) + 1); 
            } while (matriz[coord.getFila()][coord.getColumna()] != '.');
            serpientes[i] = new Serpiente(coord);
            matriz[serpientes[i].getCoordenada().getFila()][serpientes[i].getCoordenada().getColumna()] = 'S';
        }

        String[] colores = {"Rojo", "Verde", "Azul", "Amarillo", "Morado"};
        int[] puntuaciones = {10, 20, 30, 40, 50};
        for (int i = 0; i < 5; i++) {
            Coordenada coord;
            do {
                coord = new Coordenada(rand.nextInt(8) + 1, rand.nextInt(8) + 1); 
            } while (matriz[coord.getFila()][coord.getColumna()] != '.');
            Gema gema = new Gema(coord, puntuaciones[i], colores[i]);
            gemas.add(gema);
            matriz[coord.getFila()][coord.getColumna()] = '*';
            puntosMaximos += puntuaciones[i];
        }

        
        do {
            objetoProtector = new ObjetoProtector(new Coordenada(rand.nextInt(8) + 1, rand.nextInt(8) + 1));
        } while (matriz[objetoProtector.getCoordenada().getFila()][objetoProtector.getCoordenada().getColumna()] != '.');
        matriz[objetoProtector.getCoordenada().getFila()][objetoProtector.getCoordenada().getColumna()] = 'o';

        do {
            personajeMaligno = new PersonajeMaligno(new Coordenada(rand.nextInt(8) + 1, rand.nextInt(8) + 1));
        } while (matriz[personajeMaligno.getCoordenada().getFila()][personajeMaligno.getCoordenada().getColumna()] != '.');
        matriz[personajeMaligno.getCoordenada().getFila()][personajeMaligno.getCoordenada().getColumna()] = 'X';

        do {
            objetoVidaExtra = new ObjetoVidaExtra(new Coordenada(rand.nextInt(8) + 1, rand.nextInt(8) + 1), 5);
        } while (matriz[objetoVidaExtra.getCoordenada().getFila()][objetoVidaExtra.getCoordenada().getColumna()] != '.');
        coordenadaVidaExtra = objetoVidaExtra.getCoordenada();
        matriz[objetoVidaExtra.getCoordenada().getFila()][objetoVidaExtra.getCoordenada().getColumna()] = '8';
    }

    public boolean moverIndianaJones(String movimiento) {
        int nuevaFila = indianaJones.getCoordenada().getFila();
        int nuevaColumna = indianaJones.getCoordenada().getColumna();

        switch (movimiento.toLowerCase()) {
            case "w":
                nuevaFila--;
                break;
            case "s":
                nuevaFila++;
                break;
            case "a":
                nuevaColumna--;
                break;
            case "d":
                nuevaColumna++;
                break;
            case "*":
                if (usuario.esPremium()) {
                    for (Gema gema : gemas) {
                        if (Math.abs(gema.getCoordenada().getFila() - nuevaFila) <= 1 && Math.abs(gema.getCoordenada().getColumna() - nuevaColumna) <= 1) {
                            nuevaFila = gema.getCoordenada().getFila();
                            nuevaColumna = gema.getCoordenada().getColumna();
                            break;
                        }
                    }
                } else {
                    return false;
                }
                break;
            default:
                return false;
        }

        if (nuevaFila < 0 || nuevaFila >= 10 || nuevaColumna < 0 || nuevaColumna >= 10 || matriz[nuevaFila][nuevaColumna] == '#') {
            return false;
        }

        matriz[indianaJones.getCoordenada().getFila()][indianaJones.getCoordenada().getColumna()] = '.';
        indianaJones.getCoordenada().setFila(nuevaFila);
        indianaJones.getCoordenada().setColumna(nuevaColumna);

        final int finalNuevaFila = nuevaFila;
        final int finalNuevaColumna = nuevaColumna;

        if (matriz[finalNuevaFila][finalNuevaColumna] == '*') {
            Gema gemaRecolectada = gemas.stream().filter(g -> g.getCoordenada().getFila() == finalNuevaFila && g.getCoordenada().getColumna() == finalNuevaColumna).findFirst().orElse(null);
            if (gemaRecolectada != null) {
                puntosRecolectados += gemaRecolectada.getPuntuacion();
                gemas.remove(gemaRecolectada);
            }
        } else if (matriz[finalNuevaFila][finalNuevaColumna] == 'o') {
            indianaJones.activarProteccion();
        } else if (matriz[finalNuevaFila][finalNuevaColumna] == 'X') {
            indianaJones.perderVidaEnemigoMaligno();
            if (indianaJones.getVidas() <= 0) {
                return false; 
            }
            resetearIndianaJones();
            imprimir();
            return true;
        } else if (matriz[finalNuevaFila][finalNuevaColumna] == '8') {
            indianaJones.ganarVida();
            coordenadaVidaExtra = null; 
        } else if (matriz[finalNuevaFila][finalNuevaColumna] == 'S') {
            indianaJones.perderVida();
            if (indianaJones.getVidas() <= 0) {
                return false; 
            }
            resetearIndianaJones();
            imprimir();
            return true;
        }

        matriz[finalNuevaFila][finalNuevaColumna] = indianaJones.tieneProteccion() ? 'P' : '9'; 
        contadorMovimientos++; 

        if (contadorMovimientos >= 5 && coordenadaVidaExtra != null) {
            matriz[coordenadaVidaExtra.getFila()][coordenadaVidaExtra.getColumna()] = '.';
            coordenadaVidaExtra = null; 
        }

        return true;
    }

    private void resetearIndianaJones() {
        Coordenada posicionInicial = indianaJones.getPosicionInicial();
        indianaJones.resetearPosicion();
        matriz[indianaJones.getCoordenada().getFila()][indianaJones.getCoordenada().getColumna()] = '.'; 
        matriz[posicionInicial.getFila()][posicionInicial.getColumna()] = indianaJones.tieneProteccion() ? 'P' : '9'; 
    }

    private void moverPersonaje(Personaje personaje, char simbolo) {
        Random rand = new Random();
        int intentos = 0;
        boolean movido = false;
        while (intentos < 3 && !movido) {
            int direccion = rand.nextInt(4);
            int nuevaFila = personaje.getCoordenada().getFila();
            int nuevaColumna = personaje.getCoordenada().getColumna();

            switch (direccion) {
                case 0:
                    nuevaFila--;
                    break;
                case 1:
                    nuevaFila++;
                    break;
                case 2:
                    nuevaColumna--;
                    break;
                case 3:
                    nuevaColumna++;
                    break;
            }

            if (nuevaFila >= 0 && nuevaFila < 10 && nuevaColumna >= 0 && nuevaColumna < 10 && matriz[nuevaFila][nuevaColumna] == '.') {
                matriz[personaje.getCoordenada().getFila()][personaje.getCoordenada().getColumna()] = '.';
                personaje.getCoordenada().setFila(nuevaFila);
                personaje.getCoordenada().setColumna(nuevaColumna);
                matriz[nuevaFila][nuevaColumna] = simbolo;
                movido = true;
            } else {
                intentos++;
            }
        }
        if (!movido) {
            for (int i = 0; i < 4 && !movido; i++) {
                int nuevaFila = personaje.getCoordenada().getFila();
                int nuevaColumna = personaje.getCoordenada().getColumna();

                switch (i) {
                    case 0:
                        nuevaFila--;
                        break;
                    case 1:
                        nuevaFila++;
                        break;
                    case 2:
                        nuevaColumna--;
                        break;
                    case 3:
                        nuevaColumna++;
                        break;
                }

                if (nuevaFila >= 0 && nuevaFila < 10 && nuevaColumna >= 0 && nuevaColumna < 10 && matriz[nuevaFila][nuevaColumna] == '.') {
                    matriz[personaje.getCoordenada().getFila()][personaje.getCoordenada().getColumna()] = '.';
                    personaje.getCoordenada().setFila(nuevaFila);
                    personaje.getCoordenada().setColumna(nuevaColumna);
                    matriz[nuevaFila][nuevaColumna] = simbolo;
                    movido = true;
                }
            }
        }
    }

    public void moverSerpientesYMaligno() {
        for (Serpiente serpiente : serpientes) {
            moverPersonaje(serpiente, 'S');
        }
        moverPersonaje(personajeMaligno, 'X');
    }

    public void imprimir() {
        final String RESET = "\u001B[0m";
        final String RED = "\u001B[31m";
        final String GREEN = "\u001B[32m";
        final String BLUE = "\u001B[34m";
        final String YELLOW = "\u001B[33m";
        final String MAGENTA = "\u001B[35m";

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                boolean isGema = false;
                for (Gema gema : gemas) {
                    if (gema.getCoordenada().getFila() == i && gema.getCoordenada().getColumna() == j) {
                        switch (gema.getColor()) {
                            case "Rojo":
                                System.out.print(RED + "*" + RESET + " ");
                                break;
                            case "Verde":
                                System.out.print(GREEN + "*" + RESET + " ");
                                break;
                            case "Azul":
                                System.out.print(BLUE + "*" + RESET + " ");
                                break;
                            case "Amarillo":
                                System.out.print(YELLOW + "*" + RESET + " ");
                                break;
                            case "Morado":
                                System.out.print(MAGENTA + "*" + RESET + " ");
                                break;
                        }
                        isGema = true;
                        break;
                    }
                }
                if (!isGema) {
                    System.out.print(matriz[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("PUNTOS: " + puntosRecolectados);
        System.out.println("VIDAS: " + Math.max(indianaJones.getVidas(), 0)); 
    }

    public boolean todosPuntosRecolectados() {
        return puntosRecolectados >= puntosMaximos;
    }

    public int getPuntosRecolectados() {
        return puntosRecolectados;
    }
}
