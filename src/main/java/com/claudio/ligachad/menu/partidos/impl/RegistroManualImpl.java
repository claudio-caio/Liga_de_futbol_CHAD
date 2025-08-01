package com.claudio.ligachad.menu.partidos.impl;
import com.claudio.ligachad.modelo.Partido;
import com.claudio.ligachad.util.UtilExportadorCSV;
import com.claudio.ligachad.servicios.gestion.ServicioPartido;
import com.claudio.ligachad.servicios.gestion.impl.ServicioPartidoImpl;
import com.claudio.ligachad.menu.partidos.MenuPartidos;
import com.claudio.ligachad.menu.partidos.RegistroPartidos;

public class RegistroManualImpl implements RegistroPartidos {
    private final ServicioPartido gestionPartidos = new ServicioPartidoImpl();
    private final MenuPartidos menuPartidos;

    public RegistroManualImpl(MenuPartidos menuPartidos) {
        this.menuPartidos = menuPartidos;
    }

    @Override
    public void registrarPartido(Partido partido) {
        try {
            menuPartidos.getClass().getMethod("mostrarJugadoresDisponibles", partido.getClass()).invoke(menuPartidos, partido);
        } catch (Exception e) {}
        while (true) {
            String nombre = UtilExportadorCSV.leerTexto("Jugador que anotó gol (ENTER para terminar): ");
            if (nombre.isBlank()) break;

            com.claudio.ligachad.modelo.Jugador jugador = partido.getEquipoLocal().buscarJugador(nombre);
            if (jugador == null) {
                jugador = partido.getEquipoVisitante().buscarJugador(nombre);
            }

            if (jugador != null) {
                gestionPartidos.asignarGol(partido, jugador, 1);
            } else {
                System.out.println(" Jugador no encontrado.");
            }
        }
        try {
            menuPartidos.getClass().getMethod("asignarMinutosEIngresos", com.claudio.ligachad.modelo.Equipo.class, int.class)
                .invoke(menuPartidos, partido.getEquipoLocal(), 40);
            menuPartidos.getClass().getMethod("asignarMinutosEIngresos", com.claudio.ligachad.modelo.Equipo.class, int.class)
                .invoke(menuPartidos, partido.getEquipoVisitante(), 40);
        } catch (Exception e) {}
        try {
            menuPartidos.getClass().getMethod("mostrarResultadoYGanador", partido.getClass())
                .invoke(menuPartidos, partido);
        } catch (Exception e) {}
    }
}