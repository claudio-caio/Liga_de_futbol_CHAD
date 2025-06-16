package com.claudio.ligachad.modelo;


public class Suplente extends Jugador {
    private int partidosIngresados;
    private int minutosJugados;

    public Suplente(String nombre, int edad) {
        super(nombre, edad);
        this.partidosIngresados = 0;
        this.minutosJugados = 0;
    }

    public int getPartidosIngresados() {
        return partidosIngresados;
    }

    public void incrementarPartidosIngresados() {
        this.partidosIngresados++;
    }

    public int getMinutosJugados() {
        return minutosJugados;
    }

    public void agregarMinutos(int minutos) {
        this.minutosJugados += minutos;
    }

    @Override
    public boolean esTitular() {
        return false;
    }
}

