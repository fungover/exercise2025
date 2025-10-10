package org.example.users;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import org.jboss.logging.Logger;

@ApplicationScoped
public class UserService {

    Logger logger = Logger.getLogger(UserService.class);

    UserRepository userRepository;

    Event<UserCreatedEvent> userCreatedEventPublisher;

    public UserService(){}

    @Inject
    public UserService(UserRepository userRepository,
                       Event<UserCreatedEvent> userCreatedEventPublisher) {
        this.userRepository = userRepository;
        this.userCreatedEventPublisher = userCreatedEventPublisher;
    }

    public void addUser(@Valid User user) {
        //Spara användaren i databas
        userRepository.save(user);
        //Skapa och Posta event om ny user
        UserCreatedEvent userCreatedEvent = new UserCreatedEvent(user.name());
        userCreatedEventPublisher.fireAsync(userCreatedEvent);
        logger.infov("Created user {0} with employment status {1}", user.name(), user.employed());
    }
}
