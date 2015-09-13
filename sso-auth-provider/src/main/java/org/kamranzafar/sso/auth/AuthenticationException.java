package org.kamranzafar.sso.auth;

/**
 * Created by kamran on 13/08/15.
 */
public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
