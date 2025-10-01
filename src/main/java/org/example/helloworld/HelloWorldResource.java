package org.example.helloworld;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("hello")
public class HelloWorldResource {

    HelloWorldService helloWorldService;

    public HelloWorldResource(){}

    @Inject
    public HelloWorldResource(HelloWorldService helloWorldService){
        this.helloWorldService = helloWorldService;
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Hello hello(@QueryParam("name") String name) {
        return helloWorldService.createHelloMessage(name);
    }

    @GET
    @Path("/guestbook")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<String> guestBook(){
        return helloWorldService.guestBook();
    }


}
