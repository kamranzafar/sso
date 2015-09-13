package org.kamranzafar.sso.server.rest;

import org.kamranzafar.sso.SSOToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kamran on 13/08/15.
 */
@RestController
@RequestMapping("/verify")
public class TokenVerificationResource extends BaseResource {
    @RequestMapping
    @ResponseBody
    public boolean verifyCookie(@CookieValue(value = "token", defaultValue = "") String token, @CookieValue(value =
            "uid", defaultValue = "") String uid) {
        if (ssoTokenRegistry.validate(token, uid)) {
            return true;
        }

        return false;
    }

    @RequestMapping("{token}/{user}")
    @ResponseBody
    public boolean verify(@PathVariable("token") String token, @PathVariable("user") String user, HttpServletRequest
            request) {
        if (request.getHeader("user-agent") == null || !request.getHeader("user-agent").equals(CLIENT_USER_AGENT)) {
            return false;
        }

        SSOToken ssoToken = ssoTokenRegistry.lookup(token);

        System.out.println("++++++++++++++++++++ REQUEST {token:" + token + ", user:" + user + "} - "
                + "SSO_TOKEN {" + ssoToken + "} - Matched: " +
                ssoTokenRegistry.buildUid(ssoToken.getToken(), user).equals(ssoToken.getUid()));

        if (ssoToken != null) {
            if (ssoTokenRegistry.buildUid(ssoToken.getToken(), user).equals(ssoToken.getUid())) {
                return true;
            }
        }

        return false;
    }
}
