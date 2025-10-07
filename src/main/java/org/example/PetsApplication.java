package org.example;


import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("api")
public class PetsApplication extends Application {
    // Needed to enable Jakarta REST and specify path.
}
