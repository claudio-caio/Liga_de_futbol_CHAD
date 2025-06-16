package com.claudio.ligachad.modelo;

public class Titular extends Jugador {
    private int minutosJugados;

    public Titular(String nombre, int edad) {
        super(nombre, edad);
        this.minutosJugados = 0;
    }

    public int getMinutosJugados() {
        return minutosJugados;
    }

    public void agregarMinutos(int minutos) {
        this.minutosJugados += minutos;
    }

    @Override
    public boolean esTitular() {
        return true;
    }
}
