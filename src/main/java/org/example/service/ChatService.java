package org.example.service;

/*
For future implementation this interface could also contain for example sendAudio for voice message, sendImage
in order to send images and so forth.
*/

public interface ChatService {
    void sendMessage(String message);
}
