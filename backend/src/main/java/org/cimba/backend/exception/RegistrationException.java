package org.cimba.backend.exception;

/**
 * Exception thrown when an error occurs during user registration.
 */
public class RegistrationException extends UserManagementException {

    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
