package org.kamranzafar.sso.auth.ldap;

import org.kamranzafar.sso.auth.Authorization;

/**
 * Created by kamran on 12/08/15.
 */
public class LdapAuthorization implements Authorization {
    private String id;
    private String name;

    public LdapAuthorization(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
