package org.kamranzafar.sso.auth;

/**
 * Created by kamran on 13/08/15.
 */
public class UserAuthentication implements Authentication {
    private UserPrincipal userPrincipal;
    private UserCredential userCredential;

    public UserAuthentication(UserPrincipal userPrincipal, UserCredential userCredential) {
        this.userPrincipal = userPrincipal;
        this.userCredential = userCredential;
    }

    public Principal getPrincipal() {
        return userPrincipal;
    }

    public Credential getCredential() {
        return userCredential;
    }
}
