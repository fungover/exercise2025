package org.example.pets;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class PetApp extends Application {
    // Needed to enable Jakarta REST and specify path.
}
