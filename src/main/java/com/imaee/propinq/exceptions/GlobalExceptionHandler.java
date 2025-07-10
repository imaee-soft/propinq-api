package com.imaee.propinq.exceptions;

import com.imaee.propinq.exceptions.custom_exceptions.DuplicateEmailException;
import com.imaee.propinq.exceptions.custom_exceptions.DuplicateUserNameException;
import com.imaee.propinq.exceptions.custom_exceptions.EmailSendingException;
import com.imaee.propinq.exceptions.custom_exceptions.PhoneNumberParseException;
import com.imaee.propinq.exceptions.data.ExceptionMessage;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.imaee.propinq.exceptions.data.ExceptionMessage.of;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String EMAIL_SENDING_EXCEPTION_MESSAGE =
            "User registered but activation email could not be sent. Please contact support.";

    @ExceptionHandler({
            DuplicateEmailException.class,
            DuplicateUserNameException.class,
            PhoneNumberParseException.class,
            IllegalArgumentException.class
    })
    @ResponseStatus(BAD_REQUEST)
    public ExceptionMessage handleUserInputExceptions(RuntimeException ex) {
        return of(ex.getMessage(), 400);
    }

    @ExceptionHandler(EmailSendingException.class)
    @ResponseStatus(SERVICE_UNAVAILABLE)
    public ExceptionMessage handleEmailSendingException() {
        return of(EMAIL_SENDING_EXCEPTION_MESSAGE, 503);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionMessage handleJakartaValidationsException(MethodArgumentNotValidException ex) {
        return of(ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage(), 400);
    }
}