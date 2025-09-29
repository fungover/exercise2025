package org.example;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetsResource {

    @Inject
    Service service;

    @POST
    public Response adopt(@Valid PetDTO dto) {
        PetDTO saved = service.adopt(dto);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }


}
