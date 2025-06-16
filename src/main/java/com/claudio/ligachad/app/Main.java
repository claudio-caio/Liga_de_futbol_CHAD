package com.claudio.ligachad.app;

import com.claudio.ligachad.modelo.*;
import com.claudio.ligachad.servicios.ServicioEquipo;
import com.claudio.ligachad.servicios.ServicioEstadisticas;
import com.claudio.ligachad.servicios.ServicioJugador;
import com.claudio.ligachad.servicios.ServicioPartido;
import com.claudio.ligachad.util.UtilExportadorCSV;
import com.claudio.ligachad.util.Validador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    // Listas principales de equipos (en memoria)
    private static final List<Equipo> equipos = new ArrayList<>();

    // Instancia de servicios
    private static final ServicioJugador servicioJugador = new ServicioJugador();
    private static final ServicioEquipo servicioEquipo = new ServicioEquipo();
    private static final ServicioPartido servicioPartido = new ServicioPartido();
    private static final ServicioEstadisticas servicioEstadisticas = new ServicioEstadisticas(equipos);

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    registrarJugador();
                    break;
                case 2:
                    crearEquipo();
                    break;
                case 3:
                    agregarJugadorAEquipo();
                    break;
                case 4:
                    registrarPartido();
                    break;
                case 5:
                    mostrarJugadores();
                    break;
                case 6:
                    mostrarGoleadorLiga();
                    break;
                case 7:
                    mostrarPromedioGolesEquipo();
                    break;
                case 8:
                    mostrarRankingEquipos();
                    break;
                case 9:
                    transferirJugador();
                    break;
                case 10:
                    exportarJugadoresCSV();
                    break;
                case 11:
                    mostrarMenuReportes();
                    break;
                case 0:
                    System.out.println("¡Gracias por usar Liga Chad!");
                    break;
                default:
                    System.out.println("Opción inválida. Intenta de nuevo.");
                    break;
            }


        } while (opcion != 0);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Liga de Fútbol Chad ---");
        System.out.println("1. Registrar jugador");
        System.out.println("2. Crear equipo");
        System.out.println("3. Agregar jugador a equipo");
        System.out.println("4. Registrar partido");
        System.out.println("5. Mostrar jugadores");
        System.out.println("6. Mostrar goleador de la liga");
        System.out.println("7. Mostrar promedio de goles por equipo");
        System.out.println("8. Mostrar ranking de equipos");
        System.out.println("9. Transferir jugador");
        System.out.println("10. Exportar jugadores de un equipo a CSV");
        System.out.println("11. Reportes");
        System.out.println("0. Salir");
        System.out.print("Elige una opción: ");
    }

    private static int leerEntero() {
        while (!scanner.hasNextInt()) {
            System.out.println("Error: Debes ingresar un número entero.");
            scanner.next(); // descartar entrada inválida
        }
        return scanner.nextInt();
    }

    private static void registrarJugador() {
        scanner.nextLine(); // Limpiar buffer

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        if (!Validador.validarNombre(nombre) || nombre.trim().length() <= 4) {
            System.out.println("El nombre debe tener más de 4 caracteres y no estar vacío. Registro cancelado.");
            return;
        }

        System.out.print("Edad: ");
        int edad = leerEntero();
        if (!Validador.validarEdad(edad)) {
            System.out.println("La edad debe estar entre 18 y 50 años. Registro cancelado.");
            return;
        }

        System.out.print("Tipo (1=Titular, 2=Suplente): ");
        int tipo = leerEntero();

        Jugador jugador;
        if (tipo == 1) {
            jugador = new Titular(nombre, edad);
        } else if (tipo == 2) {
            jugador = new Suplente(nombre, edad);
        } else {
            System.out.println("Tipo inválido.");
            return;
        }

        servicioJugador.registrarJugador(jugador);
        System.out.println("Jugador registrado: " + jugador.getNombre());
    }

    private static void crearEquipo() {
        scanner.nextLine();
        System.out.print("Nombre del equipo: ");
        String nombre = scanner.nextLine();

        Equipo equipo = servicioEquipo.crearEquipo(nombre);
        equipos.add(equipo);

        System.out.println("Equipo creado: " + equipo.getNombre());
    }

    private static void agregarJugadorAEquipo() {
        scanner.nextLine();
        System.out.print("Nombre del equipo: ");
        String nombreEquipo = scanner.nextLine();

        Equipo equipo = servicioEquipo.buscarEquipoPorNombre(nombreEquipo);
        if (equipo == null) {
            System.out.println("Equipo no encontrado.");
            return;
        }

        System.out.print("Nombre del jugador a agregar: ");
        String nombreJugador = scanner.nextLine();

        Jugador jugador = servicioJugador.buscarJugador(nombreJugador);
        if (jugador == null) {
            System.out.println("Jugador no encontrado.");
            return;
        }

        servicioEquipo.agregarJugadorAEquipo(equipo, jugador);
        System.out.println("Jugador agregado al equipo.");
    }

    private static void registrarPartido() {
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

        Partido partido = servicioPartido.registrarPartido(local, visitante);

        // Mensajes de depuración para ver cuántos jugadores tiene cada equipo
        System.out.println("[DEBUG] Jugadores en equipo local (" + local.getNombre() + "): " + local.getJugadores().size());
        System.out.println("[DEBUG] Jugadores en equipo visitante (" + visitante.getNombre() + "): " + visitante.getJugadores().size());

        // Mostrar jugadores de ambos equipos
        System.out.println("\nJugadores del equipo local: " + local.getNombre());
        for (int i = 0; i < local.getJugadores().size(); i++) {
            System.out.println((i+1) + ". " + local.getJugadores().get(i).getNombre());
        }
        System.out.println("\nJugadores del equipo visitante: " + visitante.getNombre());
        for (int i = 0; i < visitante.getJugadores().size(); i++) {
            System.out.println((i+1) + ". " + visitante.getJugadores().get(i).getNombre());
        }

        // Permitir asignar goles a cualquier jugador de ambos equipos hasta que el usuario decida salir
        while (true) {
            System.out.print("¿A qué equipo pertenece el jugador? (1=Local, 2=Visitante, 0=Salir): ");
            int opcionEquipo = leerEntero();
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
            int indiceJugador = leerEntero() - 1;
            if (indiceJugador < 0 || indiceJugador >= equipoSeleccionado.getJugadores().size()) {
                System.out.println("Selección inválida de jugador.");
                continue;
            }
            Jugador jugador = equipoSeleccionado.getJugadores().get(indiceJugador);
            System.out.print("Cantidad de goles: ");
            int goles = leerEntero();
            servicioPartido.asignarGol(partido, jugador, goles);
        }

        // Registrar minutos jugados de titulares
        for (Equipo equipo : new Equipo[]{local, visitante}) {
            System.out.println("\nRegistrar minutos jugados de titulares para el equipo: " + equipo.getNombre());
            for (Jugador j : equipo.getJugadores()) {
                if (j instanceof Titular) {
                    System.out.print("Minutos jugados por " + j.getNombre() + ": ");
                    int minutos = leerEntero();
                    ((Titular) j).agregarMinutos(minutos);
                }
            }
        }

        // Registrar ingresos de suplentes
        for (Equipo equipo : new Equipo[]{local, visitante}) {
            System.out.println("\nRegistrar ingresos de suplentes para el equipo: " + equipo.getNombre());
            for (Jugador j : equipo.getJugadores()) {
                if (j instanceof Suplente) {
                    scanner.nextLine(); // Limpiar buffer antes de preguntar
                    System.out.print("¿Ingresó el suplente " + j.getNombre() + "? (s/n): ");
                    String ingreso = scanner.nextLine();
                    if (ingreso.equalsIgnoreCase("s")) {
                        ((Suplente) j).incrementarPartidosIngresados();
                        System.out.print("Minutos jugados por " + j.getNombre() + ": ");
                        int minutos = leerEntero();
                        ((Suplente) j).agregarMinutos(minutos);
                    }
                }
            }
        }
    }

    private static void mostrarJugadores() {
        System.out.println("\nListado de jugadores:");
        for (Jugador j : servicioJugador.listarJugadores()) {
            String tipo = j.esTitular() ? "Titular" : "Suplente";
            String equipo = (j.getEquipo() != null) ? j.getEquipo().getNombre() : "Sin equipo";
            System.out.println(tipo + " - " + j.getNombre() + ", Edad: " + j.getEdad() + ", Goles: " + j.getGoles() + ", Equipo: " + equipo);
        }
    }

    private static void mostrarGoleadorLiga() {
        Jugador goleador = servicioEstadisticas.obtenerGoleador();
        if (goleador != null) {
            System.out.println("Goleador de la liga: " + goleador.getNombre() + " con " + goleador.getGoles() + " goles.");
        } else {
            System.out.println("No hay goleador registrado.");
        }
    }

    private static void mostrarPromedioGolesEquipo() {
        scanner.nextLine();
        System.out.print("Nombre del equipo: ");
        String nombreEquipo = scanner.nextLine();

        Equipo equipo = servicioEquipo.buscarEquipoPorNombre(nombreEquipo);
        if (equipo == null) {
            System.out.println("Equipo no encontrado.");
            return;
        }

        double promedio = servicioEstadisticas.promedioGolesPorEquipo(equipo);
        System.out.printf("Promedio de goles por jugador en el equipo %s: %.2f%n", equipo.getNombre(), promedio);
    }

    private static void mostrarRankingEquipos() {
        System.out.println("\nRanking de equipos por goles:");
        List<Equipo> ranking = servicioEstadisticas.rankingEquipos();
        int posicion = 1;
        for (Equipo e : ranking) {
            System.out.println(posicion + ". " + e.getNombre() + " - Goles: " + e.getTotalGoles());
            posicion++;
        }
    }

    private static void transferirJugador() {
        scanner.nextLine();

        System.out.print("Nombre del jugador a transferir: ");
        String nombreJugador = scanner.nextLine();

        Jugador jugador = servicioJugador.buscarJugador(nombreJugador);
        if (jugador == null) {
            System.out.println("Jugador no encontrado.");
            return;
        }

        System.out.print("Equipo origen: ");
        String equipoOrigenNombre = scanner.nextLine();

        System.out.print("Equipo destino: ");
        String equipoDestinoNombre = scanner.nextLine();

        Equipo equipoOrigen = servicioEquipo.buscarEquipoPorNombre(equipoOrigenNombre);
        Equipo equipoDestino = servicioEquipo.buscarEquipoPorNombre(equipoDestinoNombre);

        if (equipoOrigen == null || equipoDestino == null) {
            System.out.println("Uno de los equipos no existe.");
            return;
        }

        servicioEquipo.transferirJugador(jugador, equipoOrigen, equipoDestino);
        System.out.println("Jugador transferido exitosamente.");
    }

    private static void exportarJugadoresCSV() {
        scanner.nextLine();
        System.out.print("Nombre del equipo a exportar: ");
        String nombreEquipo = scanner.nextLine();
        Equipo equipo = servicioEquipo.buscarEquipoPorNombre(nombreEquipo);
        if (equipo == null) {
            System.out.println("Equipo no encontrado.");
            return;
        }
        UtilExportadorCSV.exportarJugadores(equipo);
    }

    private static void mostrarMenuReportes() {
        System.out.println("\n--- Reportes ---");
        System.out.println("1. Reporte General de Liga");
        System.out.println("2. Reporte de Equipo");
        System.out.print("Elige una opción: ");
        int opcion = leerEntero();
        switch (opcion) {
            case 1:
                reporteGeneralLiga();
                break;
            case 2:
                reporteDeEquipo();
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }

    // Métodos para los reportes (lógica a implementar)
    private static void reporteGeneralLiga() {
        System.out.println("[Reporte General de Liga]");
        // Total de goles
        int totalGoles = 0;
        Jugador jugadorMasEficiente = null;
        double mejorPromedio = 0;
        Equipo equipoMaxGoles = null;
        int maxGolesEquipo = 0;

        for (Equipo equipo : equipos) {
            int golesEquipo = equipo.getTotalGoles();
            totalGoles += golesEquipo;
            if (golesEquipo > maxGolesEquipo) {
                maxGolesEquipo = golesEquipo;
                equipoMaxGoles = equipo;
            }
            for (Jugador jugador : equipo.getJugadores()) {
                int partidos = 0;
                if (jugador instanceof com.claudio.ligachad.modelo.Titular) {
                    partidos = ((com.claudio.ligachad.modelo.Titular) jugador).getMinutosJugados() > 0 ? 1 : 0;
                } else if (jugador instanceof com.claudio.ligachad.modelo.Suplente) {
                    partidos = ((com.claudio.ligachad.modelo.Suplente) jugador).getPartidosIngresados();
                }
                double promedio = (partidos > 0) ? (double) jugador.getGoles() / partidos : 0;
                if (promedio > mejorPromedio) {
                    mejorPromedio = promedio;
                    jugadorMasEficiente = jugador;
                }
            }
        }
        System.out.println("Total de goles en la liga: " + totalGoles);
        if (jugadorMasEficiente != null) {
            System.out.println("Jugador más eficiente: " + jugadorMasEficiente.getNombre() + " (Promedio: " + String.format("%.2f", mejorPromedio) + ")");
        } else {
            System.out.println("No hay jugador eficiente registrado.");
        }
        if (equipoMaxGoles != null) {
            System.out.println("Equipo con más goles: " + equipoMaxGoles.getNombre() + " (" + maxGolesEquipo + " goles)");
        } else {
            System.out.println("No hay equipos con goles.");
        }
    }

    private static void reporteDeEquipo() {
        scanner.nextLine();
        System.out.print("Nombre del equipo para el reporte: ");
        String nombreEquipo = scanner.nextLine();
        Equipo equipo = servicioEquipo.buscarEquipoPorNombre(nombreEquipo);
        if (equipo == null) {
            System.out.println("Equipo no encontrado.");
            return;
        }
        System.out.println("[Reporte de Equipo: " + equipo.getNombre() + "]");
        // Promedio de goles de sus jugadores
        double promedio = servicioEstadisticas.promedioGolesPorEquipo(equipo);
        System.out.println("Promedio de goles por jugador: " + String.format("%.2f", promedio));
        // Jugadores que no han anotado goles
        System.out.println("Jugadores que no han anotado goles:");
        boolean haySinGoles = false;
        for (Jugador j : equipo.getJugadores()) {
            if (j.getGoles() == 0) {
                System.out.println("- " + j.getNombre());
                haySinGoles = true;
            }
        }
        if (!haySinGoles) System.out.println("Todos los jugadores han anotado goles.");
        // Titular con más minutos
        Titular titularMax = equipo.getTitularMasMinutos();
        if (titularMax != null) {
            System.out.println("Titular con más minutos: " + titularMax.getNombre() + " (" + titularMax.getMinutosJugados() + " minutos)");
        } else {
            System.out.println("No hay titulares en el equipo.");
        }
        // Suplente más utilizado
        Suplente suplenteMax = null;
        int maxPartidos = -1;
        for (Jugador j : equipo.getJugadores()) {
            if (j instanceof Suplente) {
                Suplente s = (Suplente) j;
                if (s.getPartidosIngresados() > maxPartidos) {
                    maxPartidos = s.getPartidosIngresados();
                    suplenteMax = s;
                }
            }
        }
        if (suplenteMax != null) {
            System.out.println("Suplente más utilizado: " + suplenteMax.getNombre() + " (" + maxPartidos + " partidos)");
        } else {
            System.out.println("No hay suplentes en el equipo.");
        }
        // Mostrar minutos jugados de todos los suplentes
        System.out.println("Minutos jugados por suplentes:");
        boolean haySuplentes = false;
        for (Jugador j : equipo.getJugadores()) {
            if (j instanceof Suplente) {
                Suplente s = (Suplente) j;
                System.out.println("- " + s.getNombre() + ": " + s.getMinutosJugados() + " minutos");
                haySuplentes = true;
            }
        }
        if (!haySuplentes) {
            System.out.println("No hay suplentes en el equipo.");
        }
    }
}
