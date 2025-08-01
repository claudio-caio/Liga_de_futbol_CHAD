package com.claudio.ligachad.menu.partidos.impl;

import com.claudio.ligachad.modelo.Partido;
import com.claudio.ligachad.modelo.Jugador;
import com.claudio.ligachad.menu.partidos.RegistroPartidos;  
import com.claudio.ligachad.servicios.gestion.ServicioPartido;
import com.claudio.ligachad.servicios.gestion.impl.ServicioPartidoImpl;
import com.claudio.ligachad.menu.partidos.MenuPartidos;
import java.util.*;

public class RegistroAutomaticoImpl implements RegistroPartidos {
    private final ServicioPartido gestionPartidos = new ServicioPartidoImpl();
    private final MenuPartidos menuPartidos;

    public RegistroAutomaticoImpl(MenuPartidos menuPartidos) {
        this.menuPartidos = menuPartidos;
    }

    @Override
    public void registrarPartido(Partido partido) {
        Random rand = new Random();
        int totalGoles = rand.nextInt(6);
        List<Jugador> posibles = new ArrayList<>();
        posibles.addAll(partido.getEquipoLocal().getJugadores());
        posibles.addAll(partido.getEquipoVisitante().getJugadores());
        Collections.shuffle(posibles, rand);
        for (int i = 0; i < totalGoles; i++) {
            Jugador goleador = posibles.get(i % posibles.size());
            gestionPartidos.asignarGol(partido, goleador, 1);
        }
        try {
            menuPartidos.getClass().getMethod("asignarMinutosEIngresos", com.claudio.ligachad.modelo.Equipo.class, int.class)
                .invoke(menuPartidos, partido.getEquipoLocal(), 40);
            menuPartidos.getClass().getMethod("asignarMinutosEIngresos", com.claudio.ligachad.modelo.Equipo.class, int.class)
                .invoke(menuPartidos, partido.getEquipoVisitante(), 40);
        } catch (Exception e) {}
        System.out.println("Partido simulado con " + totalGoles + " goles asignados aleatoriamente.");
        try {
            menuPartidos.getClass().getMethod("mostrarResultadoYGanador", partido.getClass())
                .invoke(menuPartidos, partido);
        } catch (Exception e) {}
    }
}

