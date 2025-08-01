package com.claudio.ligachad.servicios.gestion;

import com.claudio.ligachad.modelo.Partido;
import com.claudio.ligachad.modelo.Equipo;
import com.claudio.ligachad.modelo.Jugador;
import java.util.List;
import java.util.Scanner;

public interface ServicioPartido {
    void registrarPartidoCompleto(Scanner scanner, ServicioEquipo servicioEquipo);
    Partido registrarPartido(Equipo equipoLocal, Equipo equipoVisitante);
    void asignarGol(Partido partido, Jugador jugador, int cantidad);
    List<Partido> getPartidos();
    void finalizarPartido(Partido partido);
}
