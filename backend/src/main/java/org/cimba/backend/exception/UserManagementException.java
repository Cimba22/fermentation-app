package org.cimba.backend.exception;

/**
 * Common exception for user management operations.
 */
public class UserManagementException extends RuntimeException{
    public UserManagementException(String message) {
        super(message);
    }

    public UserManagementException(String message, Throwable cause) {
        super(message, cause);
    }
}
