package org.kamranzafar.sso.client;

/**
 * Created by kamran on 13/08/15.
 */
public class SingleSignOnException extends RuntimeException {
    public SingleSignOnException(String message) {
        super(message);
    }

    public SingleSignOnException(String message, Throwable cause) {
        super(message, cause);
    }
}
