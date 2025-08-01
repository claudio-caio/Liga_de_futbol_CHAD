package com.claudio.ligachad.menu.reportes.impl;

import com.claudio.ligachad.modelo.Equipo;
import com.claudio.ligachad.modelo.Jugador;
import com.claudio.ligachad.util.UtilExportadorCSV;
import java.util.*;

public class MenuReportesImpl {
    private final List<Equipo> equipos;

    public MenuReportesImpl(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public void reporteGeneralLiga() {
        System.out.println("\n--- Reporte General de la Liga ---");
        int totalGoles = 0;
        for (Equipo equipo : equipos) {
            int golesEquipo = equipo.getJugadores().stream().mapToInt(Jugador::getGoles).sum();
            totalGoles += golesEquipo;
            System.out.println("Equipo: " + equipo.getNombre() + " | Goles: " + golesEquipo);
        }
        System.out.println("Total de goles en la liga: " + totalGoles);
    }

    public void reportePorEquipo() {
        String nombre = UtilExportadorCSV.leerTexto("Nombre del equipo: ");
        Optional<Equipo> equipoOpt = equipos.stream().filter(e -> e.getNombre().equalsIgnoreCase(nombre)).findFirst();
        if (equipoOpt.isPresent()) {
            Equipo equipo = equipoOpt.get();
            System.out.println("\n--- Reporte del Equipo: " + equipo.getNombre() + " ---");
            for (Jugador j : equipo.getJugadores()) {
                String tipo = j.esTitular() ? "Titular" : "Suplente";
                System.out.println("- " + j.getNombre() + " (" + tipo + ") | Goles: " + j.getGoles());
            }
            int golesEquipo = equipo.getJugadores().stream().mapToInt(Jugador::getGoles).sum();
            System.out.println("Total de goles del equipo: " + golesEquipo);
        } else {
            System.out.println("Equipo no encontrado.");
        }
    }
}
