package org.example.helloworld;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import org.jboss.logging.Logger;

@Path("hello")
public class HelloWorldResource {

    Logger logger = Logger.getLogger(HelloWorldResource.class);

    HelloWorldService helloWorldService;

    public HelloWorldResource(){
        logger.info("Resource default constructor");
    }

    @Inject
    public HelloWorldResource(HelloWorldService helloWorldService){
        logger.info("HelloWorldResource instance created");
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
