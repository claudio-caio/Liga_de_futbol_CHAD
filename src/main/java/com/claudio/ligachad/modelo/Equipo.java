package com.claudio.ligachad.modelo;



import java.util.List;
import java.util.ArrayList;





public class Equipo {
    private String nombre;
    private List<Jugador> jugadores;

    public Equipo(String nombre) {
        this.nombre = nombre;
        this.jugadores = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public void eliminarJugador(Jugador jugador) {
        jugadores.remove(jugador);
    }

    public Jugador buscarJugador(String nombre) {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equalsIgnoreCase(nombre)) {
                return jugador;
            }
        }
        return null;
    }

    public int getTotalGoles() {
        int total = 0;
        for (Jugador jugador : jugadores) {
            total += jugador.getGoles();
        }
        return total;
    }

    public Titular getTitularMasMinutos() {
        Titular maxTitular = null;
        int maxMinutos = -1;

        for (Jugador jugador : jugadores) {
            if (jugador instanceof Titular) {
                Titular titular = (Titular) jugador;
                if (titular.getMinutosJugados() > maxMinutos) {
                    maxMinutos = titular.getMinutosJugados();
                    maxTitular = titular;
                }
            }
        }
        return maxTitular;
    }

    public List<Suplente> getSuplentesSinIngresar() {
        List<Suplente> suplentes = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            if (jugador instanceof Suplente) {
                Suplente s = (Suplente) jugador;
                if (s.getPartidosIngresados() == 0) {
                    suplentes.add(s);
                }
            }
        }
        return suplentes;
    }
}

