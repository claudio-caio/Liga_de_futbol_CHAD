package com.claudio.ligachad.modelo;

import com.claudio.ligachad.modelo.Jugador;




public abstract class Jugador {
    protected String nombre;
    protected int edad;
    protected int goles;
    protected Equipo equipo; // Nuevo atributo

    public Jugador(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
        this.goles = 0;
        this.equipo = null;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public int getGoles() {
        return goles;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public void agregarGoles(int goles) {
        this.goles += goles;
    }

    public abstract boolean esTitular();
}
