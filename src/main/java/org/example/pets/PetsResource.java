package org.example.pets;

import jakarta.enterprise.inject.build.compatible.spi.Validation;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.annotations.Log;

import java.util.List;

@Log
@Path("/pets")
public class PetsResource {
  PetsService petsService;
  public PetsResource() {}

  @Inject
  public  PetsResource(PetsService petsService) {
    this.petsService = petsService;
  }

  @GET
  @Produces({ MediaType.APPLICATION_JSON })
  public List<Pets> pets() {
    return petsService.getPets();
  }

  @POST
  @Consumes({ MediaType.APPLICATION_JSON })
  public void adopt(@Valid Pets pet) {
    petsService.addPet(pet);
  }

  @GET()
  @Path("/{id}")
  @Produces({ MediaType.APPLICATION_JSON })
  public Pets getById(@PathParam("id") String id) {
    return petsService.getById(Long.valueOf(id));
  }

  @DELETE
  @Path("/{id}")
  public void removePet(@PathParam("id") String id) {
    petsService.removePet(Long.valueOf(id));
  }
}

