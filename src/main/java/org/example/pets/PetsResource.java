package org.example.pets;

import jakarta.enterprise.inject.build.compatible.spi.Validation;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.annotations.Log;
import org.example.validation.ErrorResponse;

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
  public Response getById(@PathParam("id") String id) {
    Pets pet = petsService.getById(id);
    if(pet == null){
      ErrorResponse errorResponse = new ErrorResponse("Cannot find pet. Pet with ID " + id + " not found", 404);
      return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
    }
    return Response.ok(pet).build();
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response removePet(@PathParam("id") String id) {
    Pets removedPet = petsService.removePet(id);

    if (removedPet == null) {
      ErrorResponse errorResponse = new ErrorResponse(
              "Cannot remove pet. Pet with ID " + id + " not found",
              404
      );
      return Response.status(Response.Status.NOT_FOUND)
              .entity(errorResponse)
              .build();
    }

    return Response.ok(removedPet).build();
  }

  @PUT
  @Path("/{id}/feed")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({MediaType.APPLICATION_JSON})
  public Response feedPet(@PathParam("id") String id, AmountRequest request) {
    petsService.feedPet((id), request.amount());
    return Response.status(200).build();
  }

  @PUT
  @Path("/{id}/play")
  @Consumes({ MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  public Response playPet(@PathParam("id") String id, AmountRequest request){
    petsService.playPet((id), request.amount());
    return Response.status(200).build();
  }
}

