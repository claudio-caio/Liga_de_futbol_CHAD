package com.claudio.ligachad.menu.otros.impl;

import java.util.Comparator;
import java.util.Optional;
//import com.claudio.ligachad.servicios.gestion.ServicioJugador;
import com.claudio.ligachad.modelo.Equipo;
import com.claudio.ligachad.modelo.Jugador;

import com.claudio.ligachad.util.UtilExportadorCSV;

//import com.claudio.ligachad.servicios.gestion.impl.ServicioJugadorImpl;
import com.claudio.ligachad.servicios.gestion.ServicioEquipo;
import com.claudio.ligachad.servicios.gestion.impl.ServicioEquipoImpl;


public class MenuExtrasImpl {
    private final java.util.List<Equipo> equipos;
    //private final ServicioJugador servicioJugador = new ServicioJugadorImpl();
    private final ServicioEquipo servicioEquipo = new ServicioEquipoImpl();
    
    public MenuExtrasImpl(java.util.List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public void transferirJugador() {
        String origenNombre = UtilExportadorCSV.leerTexto("Equipo origen: ");
        String destinoNombre = UtilExportadorCSV.leerTexto("Equipo destino: ");

        Optional<Equipo> origen = buscarEquipoPorNombre(origenNombre);
        Optional<Equipo> destino = buscarEquipoPorNombre(destinoNombre);

        if (origen.isEmpty() || destino.isEmpty()) {
            System.out.println("Uno o ambos equipos no existen.");
            return;
        }

        String nombreJugador = UtilExportadorCSV.leerTexto("Nombre del jugador a transferir: ");
        Jugador jugador = origen.get().buscarJugador(nombreJugador);
        if (jugador == null) {
            System.out.println("Jugador no encontrado en el equipo origen.");
            return;
        }
        servicioEquipo.transferirJugador(jugador, origen.get(), destino.get());
    }

    public void exportarCSV() {
        String nombre = UtilExportadorCSV.leerTexto("Nombre del equipo a exportar: ");
        Optional<Equipo> equipo = buscarEquipoPorNombre(nombre);
        equipo.ifPresentOrElse(
                UtilExportadorCSV::exportarJugadores,
                () -> System.out.println("Equipo no encontrado.")
        );
    }

    public void listarJugadoresPorEquipo() {
        for (Equipo equipo : equipos) {
            System.out.println("Equipo: " + equipo.getNombre());
            for (Jugador j : equipo.getJugadores()) {
                String tipo = j.esTitular() ? "Titular" : "Suplente";
                System.out.println(" - " + j.getNombre() + " (" + tipo + ")");
            }
        }
    }

    public void rankingEquiposPorGoles() {
        System.out.println("Ranking de Equipos por Goles:");
        equipos.stream()
                .sorted(Comparator.comparingInt(this::totalGoles).reversed())
                .forEach(e -> System.out.println(
                        e.getNombre() + " - " + totalGoles(e) + " goles"
                ));
    }

    private int totalGoles(Equipo equipo) {
        return equipo.getJugadores().stream()
                .mapToInt(Jugador::getGoles)
                .sum();
    }

    private Optional<Equipo> buscarEquipoPorNombre(String nombre) {
        return equipos.stream().filter(e -> e.getNombre().equalsIgnoreCase(nombre)).findFirst();
    }
}

