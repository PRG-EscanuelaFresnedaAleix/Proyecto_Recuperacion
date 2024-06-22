package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ranking {
    private static List<Partida> partidas = new ArrayList<>();

    public static void agregarPartida(Partida partida) {
        partidas.add(partida);
    }

    public static void mostrarTop3() {
        partidas.sort(Comparator.comparingInt(Partida::getPuntos).reversed().thenComparingLong(Partida::getTiempoEmpleado));
        System.out.println("Ranking de las 3 mejores partidas:");
        for (int i = 0; i < Math.min(3, partidas.size()); i++) {
            System.out.println(partidas.get(i));
        }
    }
}
