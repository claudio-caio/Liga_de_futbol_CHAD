package com.claudio.ligachad.servicios.estadisticas;

import com.claudio.ligachad.modelo.Equipo;
import com.claudio.ligachad.modelo.Jugador;
import java.util.List;

public interface ServicioEstadisticas {
    Jugador obtenerGoleador();
    List<Equipo> rankingEquipos();
    double promedioGolesPorEquipo(Equipo equipo);
}

