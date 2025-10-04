package org.example.pets;

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
  @Produces(MediaType.APPLICATION_JSON)
  public Response pets(@QueryParam("species") String name) {
    List<Pets> allPets = petsService.getPets();
    List<Pets> pets = petsService.findByName(name);

    if (allPets.isEmpty() ) {
      return Response.ok(List.of()).build();
    } else if (name != null) {
      return Response.ok(pets).build();
    } else
    return Response.ok(allPets).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response adopt(@Valid Pets pet) {
    Pets createdPet = petsService.addPet(pet);
    return Response.status(Response.Status.CREATED)
            .entity(createdPet)
            .build();
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
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response feedPet(@PathParam("id") String id, @Valid AmountRequest request) {
    Pets updatedPet = petsService.feedPet(id, request.amount());

    if (updatedPet == null) {
      ErrorResponse errorResponse = new ErrorResponse(
              "Cannot feed pet. Pet with ID " + id + " not found",
              404
      );
      return Response.status(Response.Status.NOT_FOUND)
              .entity(errorResponse)
              .build();
    }

    return Response.ok(updatedPet).build();
  }

  @PUT
  @Path("/{id}/play")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response playPet(@PathParam("id") String id, @Valid AmountRequest request) {
    Pets updatedPet = petsService.playPet(id, request.amount());

    if (updatedPet == null) {
      ErrorResponse errorResponse = new ErrorResponse(
              "Cannot play with pet. Pet with ID " + id + " not found",
              404
      );
      return Response.status(Response.Status.NOT_FOUND)
              .entity(errorResponse)
              .build();
    }

    return Response.ok(updatedPet).build();
  }
}

