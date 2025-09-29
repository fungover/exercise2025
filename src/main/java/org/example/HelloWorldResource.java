package org.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("hello")
public class HelloWorldResource {

    @GET @Produces({MediaType.APPLICATION_JSON})
    //to use this localhost:8080/api/hello?name=Coolfish
    //would give response "hello":Coolfish"
    public Hello hello(@QueryParam("name") String name) {
        if ((name == null) || name.trim()
                                  .isEmpty()) {
            name = "world";
        }

        return new Hello(name);
    }
}
