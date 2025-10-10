package org.example.users;

import jakarta.inject.Inject;
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

    UserService userService;

    public UserResource() {
    }

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response uploadUserInfo( @Valid User user ){
        userService.addUser(user);
        return Response.created(URI.create("")).build();
    }
}
