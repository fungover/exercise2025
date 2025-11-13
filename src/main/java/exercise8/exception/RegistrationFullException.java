package exercise8.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegistrationFullException extends RuntimeException {

    public RegistrationFullException(String message) {
        super(message);
    }

    public RegistrationFullException(String eventName, int maxParticipants) {
        super(String.format("Event '%s' is full (max %d participants)", eventName, maxParticipants));
    }
}
