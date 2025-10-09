package org.example.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.example.dto.PetDTO;
import org.example.service.PetService;

import java.net.URI;
import java.util.List;

@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetResource {

    PetService service;

    public PetResource() {}

    @Inject
    public PetResource(PetService service) {
        this.service = service;
    }

    @POST
    public Response adopt(PetDTO dto, @Context UriInfo uriInfo) {
        PetDTO created = service.adopt(dto);
        URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getId())).build();
        return Response.created(location).entity(created).build();
    }

    @GET
    public List<PetDTO> list() {
        return service.list();
    }

    @GET @Path("/{id}")
    public PetDTO get(@PathParam("id") long id) {
        return service.get(id);
    }

    @PUT @Path("/{id}/feed")
    public PetDTO feed(@PathParam("id") long id,
                       @QueryParam("amount") int amount) {
        return service.feed(id, amount);
    }

    @PUT @Path("/{id}/play")
    public PetDTO play(@PathParam("id") long id,
                       @QueryParam("amount") int amount) {
        return service.play(id, amount);
    }
}
