package com.claudio.ligachad.servicios;

import com.claudio.ligachad.modelo.Equipo;
import com.claudio.ligachad.modelo.Jugador;




import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ServicioEstadisticas {

    private List<Equipo> equipos;

    public ServicioEstadisticas(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public Jugador obtenerGoleador() {
        Jugador goleador = null;
        int maxGoles = -1;

        for (Equipo equipo : equipos) {
            for (Jugador jugador : equipo.getJugadores()) {
                if (jugador.getGoles() > maxGoles) {
                    maxGoles = jugador.getGoles();
                    goleador = jugador;
                }
            }
        }

        return goleador;
    }

    public List<Equipo> rankingEquipos() {
        List<Equipo> ranking = new ArrayList<>(equipos);
        ranking.sort(Comparator.comparingInt(Equipo::getTotalGoles).reversed());
        return ranking;
    }

    public double promedioGolesPorEquipo(Equipo equipo) {
        if (equipo == null || equipo.getJugadores().isEmpty()) {
            return 0.0;
        }

        int totalGoles = equipo.getTotalGoles();
        int jugadoresConGoles = 0;

        for (Jugador j : equipo.getJugadores()) {
            if (j.getGoles() > 0) {
                jugadoresConGoles++;
            }
        }

        return jugadoresConGoles == 0 ? 0 : (double) totalGoles / jugadoresConGoles;
    }
}

