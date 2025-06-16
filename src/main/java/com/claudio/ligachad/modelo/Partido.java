package com.claudio.ligachad.modelo;

import com.claudio.ligachad.modelo.Partido;

import java.util.*;

public class Partido {
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private Map<Jugador, Integer> golesPorJugador;

    public Partido(Equipo equipoLocal, Equipo equipoVisitante) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.golesPorJugador = new HashMap<>();
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public void registrarGol(Jugador jugador, int cantidad) {
        if (cantidad <= 0) return;

        golesPorJugador.put(jugador, golesPorJugador.getOrDefault(jugador, 0) + cantidad);
        jugador.agregarGoles(cantidad);
    }

    public Map<Jugador, Integer> getGolesPorJugador() {
        return golesPorJugador;
    }

    public Map<Equipo, Integer> getGolesPorEquipo() {
        Map<Equipo, Integer> golesPorEquipo = new HashMap<>();
        golesPorEquipo.put(equipoLocal, 0);
        golesPorEquipo.put(equipoVisitante, 0);

        for (Map.Entry<Jugador, Integer> entry : golesPorJugador.entrySet()) {
            Jugador jugador = entry.getKey();
            int goles = entry.getValue();

            if (equipoLocal.getJugadores().contains(jugador)) {
                golesPorEquipo.put(equipoLocal, golesPorEquipo.get(equipoLocal) + goles);
            } else if (equipoVisitante.getJugadores().contains(jugador)) {
                golesPorEquipo.put(equipoVisitante, golesPorEquipo.get(equipoVisitante) + goles);
            }
        }

        return golesPorEquipo;
    }

    public List<Jugador> getGoleadores() {
        List<Jugador> goleadores = new ArrayList<>(golesPorJugador.keySet());
        goleadores.sort((j1, j2) -> golesPorJugador.get(j2) - golesPorJugador.get(j1));
        return goleadores;
    }
}
