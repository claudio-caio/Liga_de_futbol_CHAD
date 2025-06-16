package com.claudio.ligachad.util;

public class Validador {
    public static boolean validarNombre(String nombre) {
        return nombre != null && !nombre.trim().isEmpty();
    }

    public static boolean validarEdad(int edad) {
        return edad >= 18 && edad <= 50;
    }

    public static boolean validarMinutos(int minutos) {
        return minutos >= 0 && minutos <= 90;
    }

    public static boolean validarPartidosIngresados(int partidos) {
        return partidos >= 0;
    }
}

