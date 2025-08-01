package com.claudio.ligachad.menu;

//import com.claudio.ligachad.dominio.Liga;
import com.claudio.ligachad.menu.equipos.impl.MenuEquiposImpl;
import com.claudio.ligachad.menu.partidos.MenuPartidos;
import com.claudio.ligachad.menu.reportes.impl.MenuReportesImpl;
import com.claudio.ligachad.menu.otros.impl.MenuExtrasImpl;
import com.claudio.ligachad.util.UtilExportadorCSV;
import com.claudio.ligachad.modelo.Equipo;
import java.util.List;
import java.util.Optional;

public class MenuPrincipal {

    private final MenuEquiposImpl menuEquipos;
    private final MenuPartidos menuPartidos;
    private final MenuReportesImpl menuReportes;
    private final MenuExtrasImpl menuExtras;
    private final List<Equipo> equipos;

    public MenuPrincipal(List<Equipo> equipos) {
        this.equipos = equipos;
        this.menuEquipos = new MenuEquiposImpl(equipos);
        this.menuPartidos = new MenuPartidos(equipos);
        this.menuReportes = new MenuReportesImpl(equipos);
        this.menuExtras = new MenuExtrasImpl(equipos);
    }

    public void mostrar() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\nMENU PRINCIPAL - LIGA CHAD");
            System.out.println("1. Crear equipo");
            System.out.println("2. Agregar jugadores a un equipo");
            System.out.println("3. Listar todos los jugadores y su tipo");
            System.out.println("4. Registrar partido");
            System.out.println("5. Reporte general de la liga");
            System.out.println("6. Reporte por equipo");
            System.out.println("7. Mostrar ranking de equipos por goles");
            System.out.println("8. Transferir jugador");
            System.out.println("9. Exportar jugadores a CSV");
            System.out.println("0. Salir");

            int opcion = UtilExportadorCSV.leerEntero("Elegí una opción: ");

            switch (opcion) {
                case 1:
                    menuEquipos.crearEquipo();
                    break;
                case 2: {
                    String nombreEquipo = UtilExportadorCSV.leerTexto("Nombre del equipo al que deseas agregar jugadores: ");
                    Optional<Equipo> equipoOpt = equipos.stream().filter(e -> e.getNombre().equalsIgnoreCase(nombreEquipo)).findFirst();
                    if (equipoOpt.isPresent()) {
                        menuEquipos.agregarJugadores(equipoOpt.get());
                    } else {
                        System.out.println("Equipo no encontrado.");
                    }
                    break;
                }
                case 3:
                    menuExtras.listarJugadoresPorEquipo();
                    break;
                case 4:
                    menuPartidos.registrarPartido();
                    break;
                case 5:
                    menuReportes.reporteGeneralLiga();
                    break;
                case 6:
                    menuReportes.reportePorEquipo();
                    break;
                case 7:
                    menuExtras.rankingEquiposPorGoles();
                    break;
                case 8:
                    menuExtras.transferirJugador();
                    break;
                case 9:
                    menuExtras.exportarCSV();
                    break;
                case 0:
                    System.out.println("Gracias por usar la Liga Chad!");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }

            if (!salir) {
                System.out.println("Presiona ENTER para continuar...");
                try { System.in.read(); } catch (Exception e) {}
            }
        }
    }
}
