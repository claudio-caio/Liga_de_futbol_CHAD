package com.claudio.ligachad.servicios;

import com.claudio.ligachad.modelo.Partido;
import com.claudio.ligachad.modelo.Equipo;
import com.claudio.ligachad.modelo.Jugador;

import java.util.ArrayList;
import java.util.List;

public class ServicioPartido {
    private List<Partido> partidos;

    public ServicioPartido() {
        this.partidos = new ArrayList<>();
    }

    public Partido registrarPartido(Equipo equipoLocal, Equipo equipoVisitante) {
        if (equipoLocal == null || equipoVisitante == null) {
            System.out.println("Equipos inválidos para registrar partido.");
            return null;
        }

        Partido nuevoPartido = new Partido(equipoLocal, equipoVisitante);
        partidos.add(nuevoPartido);
        System.out.println("Partido registrado entre " + equipoLocal.getNombre() + " y " + equipoVisitante.getNombre());
        return nuevoPartido;
    }

    public void asignarGol(Partido partido, Jugador jugador, int cantidad) {
        if (partido == null || jugador == null || cantidad <= 0) {
            System.out.println("Datos inválidos para asignar gol.");
            return;
        }

        partido.registrarGol(jugador, cantidad); // Esto ya suma los goles al jugador
        System.out.println(cantidad + " gol(es) asignado(s) a " + jugador.getNombre());
    }

    public List<Partido> getPartidos() {
        return partidos;
    }
}
