package com.claudio.ligachad.servicios.gestion;

import com.claudio.ligachad.modelo.Equipo;
import com.claudio.ligachad.modelo.Jugador;
import java.util.List;

public interface ServicioEquipo {
    void mostrarJugadores(Equipo equipo);
    Equipo crearEquipo(String nombre);
    void agregarJugadorAEquipo(Equipo equipo, Jugador jugador);
    void transferirJugador(Jugador jugador, Equipo equipoOrigen, Equipo equipoDestino);
    List<Equipo> rankingEquiposPorGoles();
    double promedioGolesPorPartido(Equipo equipo);
    Equipo buscarEquipoPorNombre(String nombre);
    List<Equipo> getEquipos();
}
