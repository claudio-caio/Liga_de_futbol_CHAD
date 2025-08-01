package com.claudio.ligachad.servicios.gestion;

import com.claudio.ligachad.modelo.Jugador;
import com.claudio.ligachad.modelo.Titular;
import com.claudio.ligachad.modelo.Suplente;
import java.util.List;

public interface ServicioJugador {
    void registrarJugador(Jugador jugador);
    List<Jugador> listarJugadores();
    Jugador buscarJugador(String nombre);
    Jugador mostrarGoleadorLiga();
    List<Suplente> mostrarSuplentesNoIngresados();
    Titular mostrarTitularMasMinutos();
}
