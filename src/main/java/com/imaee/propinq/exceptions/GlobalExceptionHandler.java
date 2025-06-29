package com.imaee.propinq.exceptions;

import com.imaee.propinq.exceptions.custom_exceptions.DuplicateEmailException;
import com.imaee.propinq.exceptions.custom_exceptions.DuplicateUserNameException;
import com.imaee.propinq.exceptions.custom_exceptions.EmailSendingException;
import com.imaee.propinq.exceptions.custom_exceptions.PhoneNumberParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateEmail(DuplicateEmailException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Email already exists");
        response.put("message", e.getMessage());
        response.put("status", HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    
    @ExceptionHandler(DuplicateUserNameException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateUserName(DuplicateUserNameException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "user name already exists");
        response.put("message", e.getMessage());
        response.put("status", HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Invalid input");
        response.put("message", e.getMessage());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<Map<String, Object>> handleEmailSending(EmailSendingException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Email service unavailable");
        response.put("message", "User registered but activation email could not be sent. Please contact support.");
        response.put("status", HttpStatus.PARTIAL_CONTENT.value());
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException e) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();
        
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            fieldErrors.put(fieldName, message);
        });
        
        response.put("error", "Validation failed");
        response.put("message", "Invalid input data");
        response.put("fieldErrors", fieldErrors);
        response.put("status", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(PhoneNumberParseException.class)
    public ResponseEntity<Map<String, Object>> handlePhoneNumberParse(PhoneNumberParseException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Invalid phone number format");
        response.put("message", e.getMessage());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Internal server error");
        response.put("message", "An unexpected error occurred. Please try again later.");
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
