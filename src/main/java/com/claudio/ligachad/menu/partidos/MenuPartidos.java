package com.claudio.ligachad.menu.partidos;

import com.claudio.ligachad.modelo.*;
import com.claudio.ligachad.servicios.gestion.ServicioPartido;
import com.claudio.ligachad.servicios.gestion.impl.ServicioPartidoImpl;
import com.claudio.ligachad.menu.partidos.impl.RegistroAutomaticoImpl;
import com.claudio.ligachad.menu.partidos.impl.RegistroManualImpl;

import java.util.*;
import com.claudio.ligachad.util.UtilExportadorCSV;

public class MenuPartidos {
    private final List<Equipo> equipos;
    private final ServicioPartido gestionPartidos = new ServicioPartidoImpl();

    public MenuPartidos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public void registrarPartido() {
        String local = UtilExportadorCSV.leerTexto("Nombre del equipo local: ");
        String visitante = UtilExportadorCSV.leerTexto("Nombre del equipo visitante: ");

        Optional<Equipo> equipoLocal = buscarEquipoPorNombre(local);
        Optional<Equipo> equipoVisitante = buscarEquipoPorNombre(visitante);

        if (equipoLocal.isEmpty() || equipoVisitante.isEmpty()) {
            System.out.println("Uno o ambos equipos no existen.");
            return;
        }

        System.out.println("1. Registrar goles manualmente");
        System.out.println("2. Simular partido automáticamente");
        int opcion = UtilExportadorCSV.leerEntero("Elegí el modo de registro: ");

        Partido partido = new Partido(equipoLocal.get(), equipoVisitante.get());

        RegistroPartidos registroStrategy;
        if (opcion == 1) {
            registroStrategy = new RegistroManualImpl(this);
        } else {
            registroStrategy = new RegistroAutomaticoImpl(this);
        }
        registroStrategy.registrarPartido(partido);

        gestionPartidos.finalizarPartido(partido);
        // Si necesitas registrar el partido en algún lado, puedes agregarlo a una lista local o similar
    }

    public void mostrarJugadoresDisponibles(Partido partido) {
        System.out.println("Jugadores disponibles para el registro:");
        partido.getEquipoLocal().getJugadores().forEach(j -> System.out.println("- " + j.getNombre() + " (Local)"));
        partido.getEquipoVisitante().getJugadores().forEach(j -> System.out.println("- " + j.getNombre() + " (Visitante)"));
    }

    public void mostrarResultadoYGanador(Partido partido) {
        int golesLocal = partido.getGolesPorEquipo().get(partido.getEquipoLocal());
        int golesVisitante = partido.getGolesPorEquipo().get(partido.getEquipoVisitante());
        String nombreLocal = partido.getEquipoLocal().getNombre();
        String nombreVisitante = partido.getEquipoVisitante().getNombre();
        System.out.println("Resultado final: " + nombreLocal + " " + golesLocal + " - " + golesVisitante + " " + nombreVisitante);
        if (golesLocal > golesVisitante) {
            System.out.println("Ganador: " + nombreLocal);
        } else if (golesVisitante > golesLocal) {
            System.out.println("Ganador: " + nombreVisitante);
        } else {
            System.out.println("Empate");
        }
    }

    public void asignarMinutosEIngresos(Equipo equipo, int minutosPorPartido) {
        for (Jugador j : equipo.getJugadores()) {
            if (j instanceof Titular) {
                Titular t = (Titular) j;
                t.agregarMinutos(minutosPorPartido);
            }
        }
        List<Jugador> suplentes = new ArrayList<>();
        for (Jugador j : equipo.getJugadores()) {
            if (j instanceof Suplente) {
                suplentes.add(j);
            }
        }
        if (!suplentes.isEmpty()) {
            Suplente s = (Suplente) suplentes.get(new Random().nextInt(suplentes.size()));
            s.incrementarPartidosIngresados();
        }
    }

    private Optional<Equipo> buscarEquipoPorNombre(String nombre) {
        return equipos.stream().filter(e -> e.getNombre().equalsIgnoreCase(nombre)).findFirst();
    }
}
