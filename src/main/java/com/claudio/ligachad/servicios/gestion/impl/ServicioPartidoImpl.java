package com.claudio.ligachad.servicios.gestion.impl;

import com.claudio.ligachad.modelo.Partido;
import com.claudio.ligachad.modelo.Equipo;
import com.claudio.ligachad.modelo.Jugador;
import com.claudio.ligachad.servicios.gestion.ServicioEquipo;
import com.claudio.ligachad.servicios.gestion.ServicioPartido;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServicioPartidoImpl implements ServicioPartido {

    private List<Partido> partidos;

    public ServicioPartidoImpl() {
        this.partidos = new ArrayList<>();
    }

    @Override
    public void registrarPartidoCompleto(Scanner scanner, ServicioEquipo servicioEquipo) {
        scanner.nextLine();

        System.out.print("Equipo local: ");
        String nombreLocal = scanner.nextLine();

        System.out.print("Equipo visitante: ");
        String nombreVisitante = scanner.nextLine();

        Equipo local = servicioEquipo.buscarEquipoPorNombre(nombreLocal);
        Equipo visitante = servicioEquipo.buscarEquipoPorNombre(nombreVisitante);

        if (local == null || visitante == null) {
            System.out.println("Alguno de los equipos no existe.");
            return;
        }

        Partido partido = registrarPartido(local, visitante);

        // Mensajes de depuración para ver cuántos jugadores tiene cada equipo
        System.out.println("[DEBUG] Jugadores en equipo local (" + local.getNombre() + "): " + local.getJugadores().size());
        System.out.println("[DEBUG] Jugadores en equipo visitante (" + visitante.getNombre() + "): " + visitante.getJugadores().size());

        // Mostrar jugadores de ambos equipos usando el servicio
        servicioEquipo.mostrarJugadores(local);
        servicioEquipo.mostrarJugadores(visitante);

        // Permitir asignar goles a cualquier jugador de ambos equipos hasta que el usuario decida salir
        while (true) {
            System.out.print("¿A qué equipo pertenece el jugador? (1=Local, 2=Visitante, 0=Salir): ");
            int opcionEquipo = leerEntero(scanner);
            if (opcionEquipo == 0) {
                break;
            }
            Equipo equipoSeleccionado = (opcionEquipo == 1) ? local : (opcionEquipo == 2) ? visitante : null;
            if (equipoSeleccionado == null) {
                System.out.println("Opción de equipo inválida.");
                continue;
            }
            if (equipoSeleccionado.getJugadores().isEmpty()) {
                System.out.println("El equipo seleccionado no tiene jugadores.");
                continue;
            }
            System.out.println("Selecciona el número del jugador que anotó:");
            for (int i = 0; i < equipoSeleccionado.getJugadores().size(); i++) {
                System.out.println((i+1) + ". " + equipoSeleccionado.getJugadores().get(i).getNombre());
            }
            int indiceJugador = leerEntero(scanner) - 1;
            if (indiceJugador < 0 || indiceJugador >= equipoSeleccionado.getJugadores().size()) {
                System.out.println("Selección inválida de jugador.");
                continue;
            }
            Jugador jugador = equipoSeleccionado.getJugadores().get(indiceJugador);
            System.out.print("Cantidad de goles: ");
            int goles = leerEntero(scanner);
            asignarGol(partido, jugador, goles);
        }

        // Registrar minutos jugados de titulares
        for (Equipo equipo : new Equipo[]{local, visitante}) {
            System.out.println("\nRegistrar minutos jugados de titulares para el equipo: " + equipo.getNombre());
            for (Jugador j : equipo.getJugadores()) {
                if (j instanceof com.claudio.ligachad.modelo.Titular) {
                    System.out.print("Minutos jugados por " + j.getNombre() + ": ");
                    int minutos = leerEntero(scanner);
                    ((com.claudio.ligachad.modelo.Titular) j).agregarMinutos(minutos);
                }
            }
        }

        // Registrar ingresos de suplentes
        for (Equipo equipo : new Equipo[]{local, visitante}) {
            System.out.println("\nRegistrar ingresos de suplentes para el equipo: " + equipo.getNombre());
            for (Jugador j : equipo.getJugadores()) {
                if (j instanceof com.claudio.ligachad.modelo.Suplente) {
                    scanner.nextLine(); // Limpiar buffer antes de preguntar
                    System.out.print("¿Ingresó el suplente " + j.getNombre() + "? (s/n): ");
                    String ingreso = scanner.nextLine();
                    if (ingreso.equalsIgnoreCase("s")) {
                        ((com.claudio.ligachad.modelo.Suplente) j).incrementarPartidosIngresados();
                        System.out.print("Minutos jugados por " + j.getNombre() + ": ");
                        int minutos = leerEntero(scanner);
                        ((com.claudio.ligachad.modelo.Suplente) j).agregarMinutos(minutos);
                    }
                }
            }
        }
    }

    private int leerEntero(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Error: Debes ingresar un número entero.");
            scanner.next(); // descartar entrada inválida
        }
        return scanner.nextInt();
    }

    @Override
    public Partido registrarPartido(Equipo equipoLocal, Equipo equipoVisitante) {
        if (equipoLocal == null || equipoVisitante == null) {
            System.out.println("Equipos inválidos para registrar partido.");
            return null;
        }

        Partido nuevoPartido = new Partido(equipoLocal, equipoVisitante);
        partidos.add(nuevoPartido);
        System.out.println("Partido registrado entre " + equipoLocal.getNombre() + " y " + equipoVisitante.getNombre());
        return nuevoPartido;
    }

    @Override
    public void asignarGol(Partido partido, Jugador jugador, int cantidad) {
        if (partido == null || jugador == null || cantidad <= 0) {
            System.out.println("Datos inválidos para asignar gol.");
            return;
        }

        partido.registrarGol(jugador, cantidad); // Esto ya suma los goles al jugador
        System.out.println(cantidad + " gol(es) asignado(s) a " + jugador.getNombre());
    }

    @Override
    public List<Partido> getPartidos() {
        return partidos;
    }

    @Override
    public void finalizarPartido(Partido partido) {
        // Aquí puedes agregar lógica para finalizar el partido, por ejemplo:
        // - Cambiar el estado del partido
        // - Registrar estadísticas finales
        // - Mostrar un mensaje
        System.out.println("Partido finalizado entre " + partido.getEquipoLocal().getNombre() + " y " + partido.getEquipoVisitante().getNombre());
    }
}
