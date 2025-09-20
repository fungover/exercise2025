package org.example.domain;

/**
 * Interface which defines the contract for message services.
 * - Does NOT know about EmailMessageService or SmsMessageService implementations.
 *
 */

public interface MessageService {
    void processMessage();
}
