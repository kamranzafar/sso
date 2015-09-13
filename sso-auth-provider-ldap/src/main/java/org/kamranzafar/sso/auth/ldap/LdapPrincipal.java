package org.kamranzafar.sso.auth.ldap;

import org.kamranzafar.sso.auth.Principal;

/**
 * Created by kamran on 12/08/15.
 */
public class LdapPrincipal implements Principal {
    private String name;
    private String userDn;

    public LdapPrincipal(String name, String userDn) {
        this.name = name;
        this.userDn = userDn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserDn() {
        return userDn;
    }

    public void setUserDn(String userDn) {
        this.userDn = userDn;
    }
}
