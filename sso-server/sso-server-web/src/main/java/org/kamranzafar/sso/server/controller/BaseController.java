package org.kamranzafar.sso.server.controller;

import org.kamranzafar.sso.SSOConstants;
import org.kamranzafar.sso.SSOToken;
import org.kamranzafar.sso.SSOTokenRegistry;
import org.kamranzafar.sso.server.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kamran on 13/08/15.
 */
public class BaseController implements SSOConstants {
    @Autowired
    protected SSOTokenRegistry ssoTokenRegistry;

    public SSOToken initializeSession(LoginRequest loginRequest, HttpServletResponse response) {
        SSOToken token = ssoTokenRegistry.register(loginRequest.getUsername());

        response.addCookie(new Cookie(PARAM_TOKEN, token.getToken()));
        response.addCookie(new Cookie(PARAM_UID, token.getUid()));

        return token;
    }

    public void destroySession(LogoutRequest logoutRequest, HttpServletResponse response) {
        destroySession(logoutRequest.getToken(), response);
    }

    public void destroySession(String token, HttpServletResponse response) {
        ssoTokenRegistry.unregister(token);

        Cookie uidCookie = new Cookie(PARAM_UID, "");
        uidCookie.setMaxAge(0);

        Cookie tokenCookie = new Cookie(PARAM_TOKEN, "");
        tokenCookie.setMaxAge(0);

        response.addCookie(uidCookie);
        response.addCookie(tokenCookie);
    }
}
