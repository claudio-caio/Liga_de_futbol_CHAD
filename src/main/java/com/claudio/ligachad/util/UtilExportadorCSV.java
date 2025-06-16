package com.claudio.ligachad.util;

import com.claudio.ligachad.modelo.Equipo;
import com.claudio.ligachad.modelo.Jugador;
import com.claudio.ligachad.modelo.Titular;


import java.io.FileWriter;
import java.io.IOException;

public class UtilExportadorCSV {

    public static void exportarJugadores(Equipo equipo) {
        String nombreArchivo = equipo.getNombre().replaceAll("\\s+", "_") + "_jugadores.csv";

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("Es titular?,Nombre,Edad,Cantidad de goles\n");
            for (Jugador jugador : equipo.getJugadores()) {
                String esTitular = jugador instanceof Titular ? "SI" : "NO";
                writer.write(esTitular + "," + jugador.getNombre() + "," + jugador.getEdad() + "," + jugador.getGoles() + "\n");
            }
            System.out.println("CSV exportado como: " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

