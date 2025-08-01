package com.claudio.ligachad.app;

import com.claudio.ligachad.datos.LigaDatos;
import com.claudio.ligachad.menu.MenuPrincipal;

public class App {
    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("   Bienvenido a la LIGA CHAD");
        System.out.println("====================================\n");

        LigaDatos repo = new LigaDatos();
        MenuPrincipal menu = new MenuPrincipal(repo.getEquipos());
        menu.mostrar();

        System.out.println("\nGracias por utilizar la Liga Chad. Hasta la próxima.");
    }
}
