package org.kamranzafar.sso.client.spring;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by kamran on 14/08/15.
 */
public class SSOAuthenticationProvider implements AuthenticationProvider {
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    public boolean supports(Class<?> aClass) {
        return false;
    }
}
