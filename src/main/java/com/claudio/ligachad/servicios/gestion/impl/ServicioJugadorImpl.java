package com.claudio.ligachad.servicios.gestion.impl;

import com.claudio.ligachad.modelo.Jugador;
import com.claudio.ligachad.modelo.Titular;
import com.claudio.ligachad.modelo.Suplente;
import com.claudio.ligachad.servicios.gestion.ServicioJugador;
import com.claudio.ligachad.util.Validador;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ServicioJugadorImpl implements ServicioJugador {
    private List<Jugador> jugadores;

    public ServicioJugadorImpl() {
        this.jugadores = new ArrayList<>();
    }

    @Override
    public void registrarJugador(Jugador jugador) {
        if (Validador.validarNombre(jugador.getNombre()) && Validador.validarEdad(jugador.getEdad())) {
            jugadores.add(jugador);
            System.out.println("Jugador registrado correctamente.");
        } else {
            System.out.println("Datos inválidos. No se registró el jugador.");
        }
    }

    @Override
    public List<Jugador> listarJugadores() {
        return jugadores;
    }

    @Override
    public Jugador buscarJugador(String nombre) {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equalsIgnoreCase(nombre)) {
                return jugador;
            }
        }
        return null;
    }

    @Override
    public Jugador mostrarGoleadorLiga() {
        return jugadores.stream()
                .max(Comparator.comparingInt(Jugador::getGoles))
                .orElse(null);
    }

    @Override
    public List<Suplente> mostrarSuplentesNoIngresados() {
        List<Suplente> sinIngresar = new ArrayList<>();
        for (Jugador j : jugadores) {
            if (j instanceof Suplente) {
                Suplente s = (Suplente) j;
                if (s.getPartidosIngresados() == 0) {
                    sinIngresar.add(s);
                }
            }
        }
        return sinIngresar;
    }

    @Override
    public Titular mostrarTitularMasMinutos() {
        Titular titularMax = null;
        int maxMinutos = -1;

        for (Jugador j : jugadores) {
            if (j instanceof Titular) {
                Titular t = (Titular) j;
                if (t.getMinutosJugados() > maxMinutos) {
                    titularMax = t;
                    maxMinutos = t.getMinutosJugados();
                }
            }
        }

        return titularMax;
    }
}
