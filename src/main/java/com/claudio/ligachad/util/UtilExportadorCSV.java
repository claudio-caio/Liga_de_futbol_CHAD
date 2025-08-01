package com.claudio.ligachad.util;

import com.claudio.ligachad.modelo.Equipo;
import com.claudio.ligachad.modelo.Jugador;
import com.claudio.ligachad.modelo.Titular;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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

    private static final Scanner scanner = new Scanner(System.in);

    public static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, ingresa un número válido.");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        return valor;
    }

    public static boolean leerBooleano(String mensaje) {
        System.out.print(mensaje + " (s/n): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        while (!respuesta.equals("s") && !respuesta.equals("n")) {
            System.out.print("Por favor, responde 's' o 'n': ");
            respuesta = scanner.nextLine().trim().toLowerCase();
        }
        return respuesta.equals("s");
    }

    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }
}
