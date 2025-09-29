package org.example;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetsResource {

    @Inject
    Service service;

    @GET
    public List<PetDTO> listPets() {
        return service.list();
    }

    @POST
    public Response adopt(@Valid PetDTO dto) {
        PetDTO saved = service.adopt(dto);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

//    @GET
//    @Path("/{id}")
//    public Response petStatus(@Valid PetDTO dto) {
//        PetDTO pet = service.get(dto.getId());
//
//        return Response.ok(pet).build();
//    }
//    @GET
//    @Path("hello")
//    @Produces({ MediaType.APPLICATION_JSON })
//    public Hello hello(@QueryParam("name") String name) {
//        if ((name == null) || name.trim().isEmpty()) {
//            name = "world";
//        }
//
//        return new Hello(name);
//    }
//
//    @GET
//    @Path("list")
//    public List<String> list() {
//        return List.of("cat", "dog", "parrot");
//    }

}
