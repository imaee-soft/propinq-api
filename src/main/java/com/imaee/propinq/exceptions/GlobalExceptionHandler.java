package com.imaee.propinq.exceptions;

import com.imaee.propinq.exceptions.custom_exceptions.DuplicateEmailException;
import com.imaee.propinq.exceptions.custom_exceptions.DuplicateUserNameException;
import com.imaee.propinq.exceptions.custom_exceptions.EmailSendingException;
import com.imaee.propinq.exceptions.custom_exceptions.PhoneNumberParseException;
import com.imaee.propinq.exceptions.data.ExceptionMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

import static com.imaee.propinq.exceptions.data.ExceptionMessage.of;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String EMAIL_SENDING_EXCEPTION_MESSAGE =
            "User registered but activation email could not be sent. Please contact support.";

    @ExceptionHandler({
            DuplicateEmailException.class,
            DuplicateUserNameException.class,
            PhoneNumberParseException.class,
            IllegalArgumentException.class,
            NoSuchElementException.class
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

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionMessage> handleResponseStatusException(ResponseStatusException ex) {
        final int status = ex.getStatusCode().value();
        return status(status).body(of(ex.getMessage(), status));
    }
}