package org.example.pets;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.annotations.Log;

@Log
@Path("/pets")
public class PetsResource {

  @GET
  @Produces({ MediaType.APPLICATION_JSON })
  public Pets pets() {
    return new Pets("Rabbit");
  }
}

