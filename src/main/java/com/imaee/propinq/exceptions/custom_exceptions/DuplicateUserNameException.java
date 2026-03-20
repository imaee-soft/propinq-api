package com.imaee.propinq.exceptions.custom_exceptions;

public class DuplicateUserNameException extends RuntimeException {
    public DuplicateUserNameException(String message) {
        super(message);
    }
    
    public DuplicateUserNameException(String message, Throwable cause) {
        super(message, cause);
    }
}

