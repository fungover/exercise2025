package org.example;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class HelloApplication extends Application {
    // Needed to enable Jakarta REST and specify path.
}
