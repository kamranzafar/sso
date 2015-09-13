package org.kamranzafar.sso.auth.ldap;

import org.kamranzafar.sso.auth.Authentication;
import org.kamranzafar.sso.auth.AuthenticationException;
import org.kamranzafar.sso.auth.AuthenticationProvider;
import org.springframework.ldap.core.AuthenticationErrorCallback;
import org.springframework.util.StringUtils;

/**
 * Created by kamran on 12/08/15.
 */
public class LdapAuthenticationProvider extends BaseProvider implements AuthenticationProvider{
    private String searchBase = "";
    private String filterFormat;

    public Authentication authenticate(Authentication authentication) {
        String userSearchFilter = authentication.getPrincipal().getName();

        if(!StringUtils.isEmpty(filterFormat)){
            userSearchFilter = String.format(filterFormat, userSearchFilter);
        }

        boolean authenticated = ldapTemplate.authenticate(searchBase, userSearchFilter, (String) authentication
                .getCredential()
                .getCredential(), new AuthenticationErrorCallback() {
            public void execute(Exception e) {
                throw new AuthenticationException(e.getMessage(), e);
            }
        });

        if(!authenticated){
            throw new AuthenticationException("Invalid Credentials");
        }

        return new LdapAuthentication(new LdapPrincipal(authentication.getPrincipal
                ().getName(), userSearchFilter), new LdapCredential());
    }

    public String getSearchBase() {
        return searchBase;
    }

    public void setSearchBase(String searchBase) {
        this.searchBase = searchBase;
    }

    public String getFilterFormat() {
        return filterFormat;
    }

    public void setFilterFormat(String filterFormat) {
        this.filterFormat = filterFormat;
    }
}
