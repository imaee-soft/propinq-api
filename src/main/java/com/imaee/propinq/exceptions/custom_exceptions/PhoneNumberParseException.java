package com.imaee.propinq.exceptions.custom_exceptions;

public class PhoneNumberParseException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "An error occurred while parsing the phone number.";

    public PhoneNumberParseException() {
        super(DEFAULT_MESSAGE);
    }

    public PhoneNumberParseException(String message) {
        super(message);
    }
}