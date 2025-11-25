package org.cimba.backend.exception;

/**
 * Exception thrown when a user is not found.
 */
public class UserNotFoundException extends UserManagementException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
