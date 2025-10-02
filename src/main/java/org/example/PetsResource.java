package org.example;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.logging.Logger;

@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetsResource {

    Service service;
    Logger logger = Logger.getLogger(PetsResource.class.getName());

    public PetsResource() {
        logger.info("Resource default constructor");
    }

    @Inject
    public PetsResource(Service service) {
        logger.info("Creating PetsResource");
        this.service = service;
    }

    //GET /api/pets → List all pets
    @GET
    public Response filterList(
            @QueryParam("offset") @DefaultValue("0") int offset,
            @QueryParam("limit") @DefaultValue("10") int limit,
            @QueryParam("species") String species,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("order") String order) {

        logger.info("Listing pets");
        List<PetDTO> pets = service.list(offset, limit, species, sortBy, order);
        if (pets.isEmpty()) {
            return Response.status(Response.Status.OK).entity(List.of("List is empty")).build();
        } else {
            // Validate sortBy and order parameter
            return Response
                    .status(Response.Status.OK)
                    .entity(service.list(offset, limit, species, sortBy, order))
                    .build();
        }

    }

    // POST api/pets → Adopt a new pet
    @POST
    public Response adopt(@Valid PetDTO dto) {
        logger.info("Adopting pet: " + dto.getName());
        PetDTO saved = service.adopt(dto);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @GET
    @Path("/{id}")
    public Response getPet(@PathParam("id") Long id) {
        logger.info("Getting pet: " + id);
        PetDTO pet = service.getPet(id);
        return Response.ok(pet).build();
    }

    @PUT
    @Path("/{id}/feed")
    public Response feedPet(@PathParam("id") Long id) {
        logger.info("Feeding pet: " + id);
        PetDTO pet = service.feedPet(id);
        return Response.ok(pet).build();
    }

    @PUT
    @Path("/{id}/play")
    public Response playPet(@PathParam("id") Long id) {
        logger.info("Playing with pet: " + id);
        PetDTO pet = service.playWithPet(id);
        return Response.ok(pet).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePet(@PathParam("id") Long id) {
        logger.info("Deleting pet: " + id);
        PetDTO removed = service.deletePet(id);
        return Response.ok(removed).build();
    }
}
