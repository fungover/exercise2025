package org.example;

/*@Path("/pets")
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

    @GET
    public List<PetDTO> list() {
        return service.list();
    }
}*/

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("pets")
public class PetsResource {

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Hello hello(@QueryParam("name") String name) {
        if ((name == null) || name.trim().isEmpty()) {
            name = "world";
        }

        return new Hello(name);
    }
}
