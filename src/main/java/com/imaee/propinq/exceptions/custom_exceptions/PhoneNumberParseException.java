package com.imaee.propinq.exceptions.custom_exceptions;

public class PhoneNumberParseException extends RuntimeException {
    public PhoneNumberParseException(String message) {
        super(message);
    }
}