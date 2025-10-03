package org.example;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("api") // All REST endpoints will be under /api.
public class PetApplication extends Application { // Inherits from the Jakarta EE application. Needed for JAX-RS (RESTEasy) to know where API starts
}
