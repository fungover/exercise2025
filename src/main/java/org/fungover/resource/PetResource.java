package org.fungover.resource;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.fungover.dto.PetDTO;
import org.fungover.service.PetService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Path("pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PetResource {

    @Inject
    PetService petService;

    @POST
    public Response adopt(@Valid PetDTO dto, @Context UriInfo uriInfo) {
        PetDTO created = petService.adopt(dto);
        URI location = uriInfo.getAbsolutePathBuilder().path(created.getId().toString()).build();
        return Response.created(location).entity(created).build();
    }

    @GET
    public List<PetDTO> list(@QueryParam("species") String species,
                             @QueryParam("offset") Integer offset,
                             @QueryParam("limit") Integer limit,
                             @QueryParam("sortBy") String sortBy,
                             @QueryParam("order") String order) {
        return petService.getAll(Optional.ofNullable(species),
                Optional.ofNullable(offset),
                Optional.ofNullable(limit),
                Optional.ofNullable(sortBy),
                Optional.ofNullable(order));
    }

    @GET @Path("{id}")
    public PetDTO get(@PathParam("id") Long id) {
        return petService.get(id);
    }

    @PUT @Path("{id}/feed")
    public PetDTO feed(@PathParam("id") Long id) {
        return petService.feed(id);
    }

    @PUT @Path("{id}/play")
    public PetDTO play(@PathParam("id") Long id) {
        return petService.play(id);
    }

    @DELETE @Path("{id}")
    public Response release(@PathParam("id") Long id) {
        petService.release(id);
        return Response.noContent().build();
    }
}
