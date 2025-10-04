package org.example.exception;

public sealed interface ErrorDetail permits MessageDetail, ViolationMessage {
}
