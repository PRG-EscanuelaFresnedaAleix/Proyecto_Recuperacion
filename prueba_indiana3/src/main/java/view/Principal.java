package view;

import controller.Juego;
import java.util.Scanner;

public class Principal {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicio();
    }

    public static void inicio() {
        Juego juego = new Juego();
        int opcion;
        do {
            System.out.println("1. Login");
            System.out.println("2. Instrucciones");
            System.out.println("3. Jugar");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    juego.login();
                    break;
                case 2:
                    mostrarInstrucciones();
                    break;
                case 3:
                    juego.jugar();
                    break;
                case 4:
                    System.out.println("Gracias por jugar. ¡Adiós!");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 4);
    }

    public static void mostrarInstrucciones() {
        System.out.println("Instrucciones del Juego:");
        System.out.println("1. Movimientos: ");
        System.out.println("   - 'w' para arriba");
        System.out.println("   - 'a' para izquierda");
        System.out.println("   - 's' para abajo");
        System.out.println("   - 'd' para derecha");
        System.out.println("   - '*' para movimiento especial (solo usuarios premium)");
        System.out.println("2. Símbolos en el tablero:");
        System.out.println("   - '9' : Indiana Jones");
        System.out.println("   - 'P' : Indiana Jones con escudo");
        System.out.println("   - 'S' : Serpiente");
        System.out.println("   - 'X' : Enemigo maligno");
        System.out.println("   - '*' : Gema");
        System.out.println("   - 'o' : Objeto protector");
        System.out.println("   - '8' : Vida extra");
        System.out.println("   - '#' : Muro");
        System.out.println("   - '.' : Espacio vacío");
        System.out.println("3. Objetivo del juego:");
        System.out.println("   - Recolectar todas las gemas para ganar puntos.");
        System.out.println("   - Evitar las serpientes y enemigos malignos.");
        System.out.println("   - Recoger objetos protectores y vidas extra para ayudarte en el juego.");
        System.out.println("   - Los usuarios premium tienen 4 vidas en lugar de 3 y un movimiento especial con '*'.");
        System.out.println();
    }
}
