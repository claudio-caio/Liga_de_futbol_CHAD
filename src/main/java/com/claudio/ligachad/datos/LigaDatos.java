package com.claudio.ligachad.datos;

import com.claudio.ligachad.modelo.Equipo;
import com.claudio.ligachad.modelo.Suplente;
import com.claudio.ligachad.modelo.Titular;

import java.util.List;
import java.util.ArrayList;

public class LigaDatos {

    private final List<Equipo> equipos = new ArrayList<>();

    public LigaDatos() {
        datosCargadosDeEquiposYJugadores();
    }

    private void datosCargadosDeEquiposYJugadores() {
        Equipo huracan = new Equipo("Huracán");
        huracan.agregarJugador(new Titular("Alejandro Gómez", 24, true));
        huracan.agregarJugador(new Titular("Sebastián Ríos", 29, false));
        huracan.agregarJugador(new Titular("Manuel Cabrera", 23, true));
        huracan.agregarJugador(new Titular("Gustavo Medina", 31, true));
        huracan.agregarJugador(new Titular("Rodrigo Núñez", 27, false));
        huracan.agregarJugador(new Suplente("Pablo Castro", 22, true));
        huracan.agregarJugador(new Suplente("Luciano Vega", 21, false));
        huracan.agregarJugador(new Suplente("Matías Herrera", 23, true));
        equipos.add(huracan);

        Equipo central = new Equipo("Central Norte");
        central.agregarJugador(new Titular("Cristian Ramírez", 26, true));
        central.agregarJugador(new Titular("Esteban Díaz", 28, false));
        central.agregarJugador(new Titular("Julián López", 24, true));
        central.agregarJugador(new Titular("Fernando Ruiz", 30, true));
        central.agregarJugador(new Titular("Hernán Aguirre", 29, false));
        central.agregarJugador(new Suplente("Tomás Giménez", 22, true));
        central.agregarJugador(new Suplente("Santiago Vargas", 20, false));
        central.agregarJugador(new Suplente("Brian Torres", 21, true));
        equipos.add(central);

        Equipo estudiantes = new Equipo("Estudiantes");
        estudiantes.agregarJugador(new Titular("Facundo Morales", 25, true));
        estudiantes.agregarJugador(new Titular("Lucas Domínguez", 27, false));
        estudiantes.agregarJugador(new Titular("Ignacio Paredes", 23, true));
        estudiantes.agregarJugador(new Titular("Joaquín García", 28, true));
        estudiantes.agregarJugador(new Titular("Daniel Pérez", 26, false));
        estudiantes.agregarJugador(new Suplente("Mauricio Sosa", 22, true));
        estudiantes.agregarJugador(new Suplente("Adrián Romero", 24, false));
        estudiantes.agregarJugador(new Suplente("Franco Toledo", 21, true));
        equipos.add(estudiantes);

        Equipo juventud = new Equipo("Juventud Unida");
        juventud.agregarJugador(new Titular("Maximiliano Ortega", 28, true));
        juventud.agregarJugador(new Titular("Carlos Luna", 27, false));
        juventud.agregarJugador(new Titular("Ricardo Godoy", 24, true));
        juventud.agregarJugador(new Titular("Leonel Mendoza", 26, true));
        juventud.agregarJugador(new Titular("Ramiro Salinas", 25, false));
        juventud.agregarJugador(new Suplente("Diego Barreto", 23, true));
        juventud.agregarJugador(new Suplente("Nicolás Cardozo", 22, false));
        juventud.agregarJugador(new Suplente("Emanuel Figueroa", 21, true));
        equipos.add(juventud);
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }
}
