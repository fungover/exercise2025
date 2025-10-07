package org.example;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/test")
public class TestResource {
    @POST
    public Response test(String input) {
        return Response.ok(input).build();
    }
}
