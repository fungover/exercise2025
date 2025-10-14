package org.fungover.resource;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.fungover.dto.PetDTO;
import org.fungover.service.PetService;

@Path("pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PetResource {

    @Inject
    PetService petService;

    @GET
    @Path("{id}")
    public PetDTO get(@PathParam("id") long id) {
        return petService.get(id);
    }


}
