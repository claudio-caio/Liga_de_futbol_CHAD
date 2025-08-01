package com.claudio.ligachad.menu.equipos.impl;

import com.claudio.ligachad.modelo.Equipo;
import com.claudio.ligachad.modelo.Jugador;
import com.claudio.ligachad.modelo.Titular;
import com.claudio.ligachad.modelo.Suplente;
import com.claudio.ligachad.servicios.gestion.ServicioEquipo;
import com.claudio.ligachad.servicios.gestion.impl.ServicioEquipoImpl;
import com.claudio.ligachad.util.UtilExportadorCSV;

import java.util.List;

public class MenuEquiposImpl {
    private final ServicioEquipo gestionEquipos = new ServicioEquipoImpl();
    private List<Equipo> equipos;
    public MenuEquiposImpl(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public MenuEquiposImpl() {
        // Constructor vacío
    }

    public void crearEquipo() {
        String nombre = UtilExportadorCSV.leerTexto("Nombre del nuevo equipo: ");
        Equipo equipo = gestionEquipos.crearEquipo(nombre); // Ajusta el método en el service si es necesario
        if (equipo != null && equipos != null) {
            equipos.add(equipo);
            System.out.println("Equipo agregado a la lista principal.");
        }
    }

    public void agregarJugadores(Equipo equipo) {
        boolean seguir = true;

        while (seguir) {
            String nombre = UtilExportadorCSV.leerTexto("Nombre del jugador: ");
            int edad = UtilExportadorCSV.leerEntero("Edad del jugador: ");
            boolean titular = UtilExportadorCSV.leerBooleano("¿Es titular?");
            boolean transferible = UtilExportadorCSV.leerBooleano("¿Es transferible?");

            Jugador jugador = titular ? new Titular(nombre, edad, transferible) : new Suplente(nombre, edad, transferible);
            gestionEquipos.agregarJugadorAEquipo(equipo, jugador);

            System.out.println("Jugadores actuales en el equipo " + equipo.getNombre() + ":");
            equipo.getJugadores().forEach(j -> {
                String tipo = j.esTitular() ? "Titular" : "Suplente";
                System.out.println("- " + j.getNombre() + " (" + tipo + ")");
            });

            seguir = UtilExportadorCSV.leerBooleano("¿Deseás seguir agregando jugadores?");
        }
    }
}
