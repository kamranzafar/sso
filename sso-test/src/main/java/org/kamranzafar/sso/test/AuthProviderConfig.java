package org.kamranzafar.sso.test;

import org.kamranzafar.sso.auth.ldap.LdapAuthenticationProvider;
import org.kamranzafar.sso.auth.ldap.LdapAuthorizationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.core.support.SimpleDirContextAuthenticationStrategy;

/**
 * Created by kamran on 13/08/15.
 */
@Configuration
public class AuthProviderConfig {

    @Bean
    public ContextSource contextSource(){
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://localhost");
        contextSource.setBase("dc=kamran,dc=local");
        contextSource.setUserDn("cn=admin,dc=kamran,dc=local");
        contextSource.setPassword("Password1");
        contextSource.setPooled(true);
        contextSource.setAuthenticationStrategy(new SimpleDirContextAuthenticationStrategy());

        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate(){
        return new LdapTemplate(contextSource());
    }

    @Bean
    public LdapAuthenticationProvider authenticationProvider(){
        LdapAuthenticationProvider ldapAuthenticationProvider = new LdapAuthenticationProvider();
        ldapAuthenticationProvider.setFilterFormat("(uid=%s)");
        ldapAuthenticationProvider.setLdapTemplate(ldapTemplate());

        return ldapAuthenticationProvider;
    }

    @Bean
    public LdapAuthorizationProvider authorizationProvider(){
        LdapAuthorizationProvider ldapAuthorizationProvider = new LdapAuthorizationProvider();
        ldapAuthorizationProvider.setLdapTemplate(ldapTemplate());

        return ldapAuthorizationProvider;
    }
}
