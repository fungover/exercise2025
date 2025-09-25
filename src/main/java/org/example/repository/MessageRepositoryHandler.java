package org.example.repository;

/* This class handles messages in a specific way.
right now it just prints the message to the console.

A little reminder for myself:
-An interface defines WHAT can be done. <---- TÄNK på detta
-A class implementing the interface defines HOW it is done. <---- å detta!
*/

public class MessageRepositoryHandler implements MessageRepository {

        @Override
        public void saveMessage(String message) {
            System.out.println("Mottaget meddelande: " + message);
        }
}
