package org.example.helloworld;

import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

import org.example.users.UserCreatedEvent;
import org.jboss.logging.Logger;

@Path("hello")
public class HelloWorldResource {

    Logger logger = Logger.getLogger(HelloWorldResource.class);

    HelloWorldService helloWorldService;

//    // Publisher
//    @Inject
//    Event<UserCreatedEvent> userCreatedEvent;

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
       // createUser(name);
        return helloWorldService.createHelloMessage(name);
    }

    @GET
    @Path("/guestbook")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<String> guestBook(){
        return helloWorldService.guestBook();
    }

//    public void createUser(String username) {
//        // ... create user logic
//        userCreatedEvent.fire(new UserCreatedEvent(username));
//    }
}





