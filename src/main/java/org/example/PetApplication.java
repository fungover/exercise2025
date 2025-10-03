package org.example;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * PetApplication activates JAX-RS and sets the base path for our API.
 *
 * Why needed:
 * - Tells Jakarta EE that we want to expose REST endpoints.
 * - Defines that all resources are available under /api.
 */
@ApplicationPath("api")
public class PetApplication extends Application {
}