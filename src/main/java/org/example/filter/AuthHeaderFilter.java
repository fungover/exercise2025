package org.example.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@RequiresAuth
public class AuthHeaderFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String authHeader =containerRequestContext.getHeaderString("X-Auth");
        if( authHeader == null || !authHeader.equals("secret"))
            containerRequestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Missing or invalid auth token")
                            .build());
    }
}
