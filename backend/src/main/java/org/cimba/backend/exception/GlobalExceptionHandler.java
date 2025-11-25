package org.cimba.backend.exception;

import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.DigestException;
import java.util.HashSet;
import java.util.Set;

import static org.cimba.backend.exception.BusinessErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exception){
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(ACCOUNT_LOCKED.getCode())
                                .businessErrorDescription(ACCOUNT_LOCKED.getDescription())
                                .error(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(DigestException.class)
    public ResponseEntity<ExceptionResponse> handleException(DigestException exception){
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(ACCOUNT_DISABLED.getCode())
                                .businessErrorDescription(ACCOUNT_DISABLED.getDescription())
                                .error(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(){
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BAD_CREDENTIALS.getCode())
                                .businessErrorDescription(BAD_CREDENTIALS.getDescription())
                                .error(BAD_CREDENTIALS.getDescription())
                                .build()
                );
    }


    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exception){
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .error(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exception){
        Set<String> errors = new HashSet<>();
        exception.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .validationErrors(errors)
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exception){
        exception.printStackTrace();
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorDescription("Internal error, contact the admin")
                                .error(exception.getMessage())
                                .build()
                );
    }










//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleException(Exception e) {
//        return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(RegistrationException.class)
//    public ResponseEntity<String> handleRegistrationException(RegistrationException e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ResponseEntity<String> handleResourceNotFoundException(NoHandlerFoundException e) {
//        return new ResponseEntity<>("Resource not found: " + e.getRequestURL(), HttpStatus.NOT_FOUND);
//    }
}
