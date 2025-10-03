package org.example.pets;

import jakarta.inject.Inject;
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
  public void adopt(Pets pet) {
    petsService.addPet(pet);
  }


}

