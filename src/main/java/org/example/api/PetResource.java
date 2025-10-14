package org.example.api;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
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
    public Response adopt(@Valid PetDTO dto, @Context UriInfo uriInfo) {
        PetDTO created = service.adopt(dto);
        URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getId())).build();
        return Response.created(location).entity(created).build();
    }

    @GET
    public Response list(
            @QueryParam("species") String species,
            @QueryParam("sortBy") @DefaultValue("id")
            @Pattern(regexp = "(?i)id|name|species|hungerLevel|happiness")
            String sortBy,
            @QueryParam("order") @DefaultValue("asc")
            @Pattern(regexp = "(?i)asc|desc")
            String order,
            @QueryParam("offset") @DefaultValue("0") @Min(0) int offset,
            @QueryParam("limit")  @DefaultValue("10") @Min(0) @Max(100) int limit
    ) {
        var page = service.list(species, sortBy, order, offset, limit);
        return Response.ok(page.items())
                .header("X-Total-Count", page.total())
                .header("X-Offset", offset)
                .header("X-Limit",  limit)
                .build();
    }

    @GET @Path("/{id}")
    public PetDTO get(@PathParam("id") long id) {
        return service.get(id);
    }

    @PUT @Path("/{id}/feed")
    public PetDTO feed(@PathParam("id") long id,
                       @QueryParam("amount") @DefaultValue("10") @Min(1) int amount) {
        return service.feed(id, amount);
    }

    @PUT @Path("/{id}/play")
    public PetDTO play(@PathParam("id") long id,
                       @QueryParam("amount") @DefaultValue("10") @Min(1) int amount) {
        return service.play(id, amount);
    }

    @DELETE @Path("/{id}")
    public Response delete(@PathParam("id") long id) {
        service.release(id);
        return Response.noContent().build();
    }
}
