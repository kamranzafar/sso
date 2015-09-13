package org.kamranzafar.sso.auth;

/**
 * Created by kamran on 13/08/15.
 */
public class UserPrincipal implements Principal {
    private String name;

    public UserPrincipal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
