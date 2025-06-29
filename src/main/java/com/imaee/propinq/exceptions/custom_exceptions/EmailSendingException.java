package com.imaee.propinq.exceptions.custom_exceptions;

public class EmailSendingException extends RuntimeException {
    public EmailSendingException(String message) {
        super(message);
    }
}
