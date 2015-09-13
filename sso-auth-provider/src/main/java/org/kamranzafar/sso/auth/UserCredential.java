package org.kamranzafar.sso.auth;

/**
 * Created by kamran on 13/08/15.
 */
public class UserCredential implements Credential {
    private String credential;

    public UserCredential(String credential) {
        this.credential = credential;
    }

    public Object getCredential() {
        return credential;
    }
}
