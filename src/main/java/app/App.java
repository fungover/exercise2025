package app;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;  //Objekten.

public class App {
    public static void main(String[] args) {
        try (WeldContainer container = new Weld().initialize()) {
            Program program = container.select(Program.class).get(); //Weld skapar programmet.
            program.run(); //Kör programmet och skriver hälsningen.
        }

    }
}
