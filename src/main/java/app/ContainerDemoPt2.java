package app;

import container.SimpleContainer;

public class ContainerDemoPt2 {
    public static void main(String[] args) {
        SimpleContainer container = new SimpleContainer();
        var program = container.getInstance(Program.class);
        program.run(); // ska skriva samma hälsning som Weld-versionen
    }

}
