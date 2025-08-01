package com.claudio.ligachad.servicios.gestion.impl;

import com.claudio.ligachad.modelo.Equipo;
import com.claudio.ligachad.modelo.Jugador;
import com.claudio.ligachad.servicios.gestion.ServicioEquipo;
import com.claudio.ligachad.util.Validador;

import java.util.ArrayList;
import java.util.List;

public class ServicioEquipoImpl implements ServicioEquipo {
    private final List<Equipo> equipos;

    public ServicioEquipoImpl() {
        this.equipos = new ArrayList<>();
    }

    @Override
    public void mostrarJugadores(Equipo equipo) {
        if (equipo == null) {
            System.out.println("Equipo no encontrado.");
            return;
        }
        System.out.println("\nJugadores del equipo: " + equipo.getNombre());
        List<Jugador> jugadores = equipo.getJugadores();
        for (int i = 0; i < jugadores.size(); i++) {
            System.out.println((i+1) + ". " + jugadores.get(i).getNombre());
        }
    }

    @Override
    public Equipo crearEquipo(String nombre) {
        if (!Validador.validarNombre(nombre)) {
            System.out.println("Nombre inválido para el equipo.");
            return null;
        }

        Equipo nuevo = new Equipo(nombre);
        equipos.add(nuevo);
        System.out.println("Equipo creado correctamente.");
        return nuevo;
    }

    @Override
    public void agregarJugadorAEquipo(Equipo equipo, Jugador jugador) {
        if (equipo == null || jugador == null) {
            System.out.println("Error: equipo o jugador nulo.");
            return;
        }
        if (!Validador.validarNombre(jugador.getNombre())) {
            System.out.println("Nombre inválido");
            return;
        }
        if (!Validador.validarEdad(jugador.getEdad())) {
            System.out.println("Edad inválida");
            return;
        }

        equipo.agregarJugador(jugador);
        jugador.setEquipo(equipo); // Asignar equipo al jugador
        System.out.println("Jugador agregado al equipo " + equipo.getNombre());
    }

    @Override
    public void transferirJugador(Jugador jugador, Equipo equipoOrigen, Equipo equipoDestino) {
        if (jugador == null || equipoOrigen == null || equipoDestino == null) {
            System.out.println("Datos inválidos para la transferencia.");
            return;
        }

        equipoOrigen.eliminarJugador(jugador);
        equipoDestino.agregarJugador(jugador);
        jugador.setEquipo(equipoDestino); // Actualiza el equipo del jugador transferido
        System.out.println("Jugador transferido de " + equipoOrigen.getNombre() + " a " + equipoDestino.getNombre());
    }

    @Override
    public List<Equipo> rankingEquiposPorGoles() {
        List<Equipo> ranking = new ArrayList<>(equipos);
        ranking.sort((e1, e2) -> Integer.compare(e2.getTotalGoles(), e1.getTotalGoles()));
        return ranking;
    }

    @Override
    public double promedioGolesPorPartido(Equipo equipo) {
        if (equipo == null || equipo.getJugadores().isEmpty()) {
            return 0;
        }

        int totalGoles = equipo.getTotalGoles();
        int cantidadPartidos = calcularPartidosJugados(equipo);

        return cantidadPartidos == 0 ? 0 : (double) totalGoles / cantidadPartidos;
    }

    // Método de ayuda para estimar partidos jugados por un equipo
    private int calcularPartidosJugados(Equipo equipo) {
        int partidosEstimados = 0;
        for (Jugador j : equipo.getJugadores()) {
            if (j.getGoles() > 0) {
                partidosEstimados++;
            }
        }
        // Redondeo simple: mínimo 1 partido si hay goles
        return partidosEstimados > 0 ? 1 : 0;
    }

    @Override
    public Equipo buscarEquipoPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return null;
        }
        for (Equipo equipo : equipos) {
            if (equipo.getNombre().equalsIgnoreCase(nombre.trim())) {
                return equipo;
            }
        }
        return null;
    }

    @Override
    public List<Equipo> getEquipos() {
        return equipos;
    }
}
