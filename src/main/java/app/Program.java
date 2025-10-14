package app;

import api.Greetings;
import jakarta.inject.Inject;

public class Program {  //Bestämmer vad som ska göras utan att ggörs.
    private final Greetings greetings;


    @Inject //Weld hjälper mig att koppla samman allt.
    public Program(Greetings greetings) {
        this.greetings = greetings;
    }



    public void run() { greetings.greet("Bekhal"); }
}
