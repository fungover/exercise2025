package org.example.api;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.dto.PetDTO;
import org.example.service.PetService;

import java.net.URI;
import java.util.List;

@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetResource {

    @Inject
    PetService service;

    // POST /pets
    @POST
    public Response create(@Valid PetDTO dto, @Context UriInfo uriInfo) {
        PetDTO created = service.create(dto);
        URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getId())).build();
        return Response.created(location).entity(created).build(); // 201 + Location + body
    }

    // GET /pets
    @GET
    public Response list(@QueryParam("offset") Integer offset,
                         @QueryParam("limit") Integer limit,
                         @QueryParam("species") String species,
                         @QueryParam("sortBy") @DefaultValue("id") String sortBy,
                         @QueryParam("order") @DefaultValue("asc") String order) {
        List<PetDTO> result = service.search(offset, limit, species, sortBy, order);
        return Response.ok(result).build();
    }


    // GET /pets/{id}
    @GET
    @Path("/{id}")
    public Response getOne(@PathParam("id") long id) {
        PetDTO dto = service.findById(id);
        return Response.ok(dto).build();
    }

    // PUT /pets/{id}/feed?amount=10
    @PUT
    @Path("/{id}/feed")
    public Response feed(@PathParam("id") long id, @DefaultValue("10") @QueryParam("amount") int amount) {
        if (amount < 0) {
            throw new BadRequestException("amount must be >= 0");
        }
        PetDTO updated = service.feed(id, amount);
        return Response.ok(updated).build();
    }

    // PUT /pets/{id}/play?amount=10
    @PUT
    @Path("/{id}/play")
    public Response play(@PathParam("id") long id, @DefaultValue("10") @QueryParam("amount") int amount) {
        if (amount < 0) {
            throw new BadRequestException("amount must be >= 0");
        }
        PetDTO updated = service.play(id, amount);
        return Response.ok(updated).build();
    }

    // DELETE /pets/{id}
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) {
        service.delete(id);
        return Response.noContent().build(); // 204
    }
}
