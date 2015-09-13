package org.kamranzafar.sso.auth.ldap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;

/**
 * Created by kamran on 12/08/15.
 */
public abstract class BaseProvider {
    @Autowired
    protected LdapTemplate ldapTemplate;

    public LdapTemplate getLdapTemplate() {
        return ldapTemplate;
    }

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }
}
