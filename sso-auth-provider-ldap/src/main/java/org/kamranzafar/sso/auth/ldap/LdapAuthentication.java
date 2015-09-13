package org.kamranzafar.sso.auth.ldap;

import org.kamranzafar.sso.auth.Authentication;
import org.kamranzafar.sso.auth.Credential;
import org.kamranzafar.sso.auth.Principal;

/**
 * Created by kamran on 13/08/15.
 */
public class LdapAuthentication implements Authentication {
    private Principal principal;
    private Credential credential;

    public LdapAuthentication(Principal principal, Credential credential) {
        this.principal = principal;
        this.credential = credential;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public Credential getCredential() {
        return credential;
    }
}
