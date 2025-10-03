package org.example.users;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.net.URI;

@Path("users")
public class UserResource {

    Logger logger = Logger.getLogger(UserResource.class);

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response uploadUserInfo( @Valid User user ){
        logger.infov("Created user {0} with employment status {1}", user.name(), user.employed());
        return Response.created(URI.create("")).build();
    }

}
