package controller;

import model.*;
import java.util.Scanner;

public class Juego {
    private Usuario usuarioActual;
    private Escenario escenario;
    private static int numeroPartida = 1;
    private static final String[] usuariosValidos = {"u1", "u2", "u3", "u4"};
    private static final String passwordValida = "1234";
    private static final Scanner scanner = new Scanner(System.in);
    private long startTime;

    public void login() {
        System.out.print("Introduce tu usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Introduce tu contraseña: ");
        String password = scanner.nextLine();

        boolean esValido = false;
        for (String u : usuariosValidos) {
            if (u.equals(usuario) && passwordValida.equals(password)) {
                esValido = true;
                break;
            }
        }

        if (esValido) {
            usuarioActual = new Usuario(usuario, password, "u3".equals(usuario));
            System.out.println("Login exitoso. Bienvenido, " + usuario);
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }

    public void jugar() {
        if (usuarioActual == null) {
            usuarioActual = new Usuario("Anónimo", "", false);
        }

        escenario = new Escenario(usuarioActual);
        escenario.inicializar();

        startTime = System.currentTimeMillis();
        boolean juegoTerminado = false;
        while (usuarioActual.getVidas() > 0 && !escenario.todosPuntosRecolectados()) {
            escenario.imprimir();
            System.out.print("Introduce tu movimiento (w/a/s/d" + (usuarioActual.esPremium() ? "/*" : "") + "): ");
            String movimiento = scanner.nextLine();
            boolean movimientoValido = escenario.moverIndianaJones(movimiento);

            if (!movimientoValido) {
                System.out.println("Movimiento no válido.");
            }

            if (usuarioActual.getVidas() <= 0) {
                juegoTerminado = true;
                break;
            }

            escenario.moverSerpientesYMaligno();

            if (escenario.todosPuntosRecolectados()) {
                System.out.println("¡Has ganado! Has recolectado todos los puntos.");
                break;
            }
        }

        long endTime = System.currentTimeMillis();
        long tiempoEmpleado = endTime - startTime;

        Partida partida = new Partida(numeroPartida++, usuarioActual, escenario.getPuntosRecolectados(), tiempoEmpleado);
        Ranking.agregarPartida(partida);

        if (juegoTerminado || usuarioActual.getVidas() <= 0) {
            System.out.println("Has perdido. Fin del juego.");
            return;
        } else {
            System.out.println("¡Has ganado! Has recolectado todos los puntos.");
            Ranking.mostrarTop3();
        }
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
}
