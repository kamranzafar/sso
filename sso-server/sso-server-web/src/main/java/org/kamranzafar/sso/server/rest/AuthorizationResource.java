package org.kamranzafar.sso.server.rest;

import org.kamranzafar.sso.auth.Authorization;
import org.kamranzafar.sso.auth.AuthorizationProvider;
import org.kamranzafar.sso.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by kamran on 12/08/15.
 */
@RestController
@RequestMapping("/auth")
public class AuthorizationResource extends BaseResource {
    @Autowired
    private AuthorizationProvider authorizationProvider;

    @RequestMapping("{username}")
    @ResponseBody
    public List<Authorization> authorization(@PathVariable("username") String username,
                                             @CookieValue(value = "token", defaultValue = "") String token,
                                             @CookieValue(value = "uid", defaultValue = "") String uid) {
        if (!ssoTokenRegistry.validate(token, uid)) {
            throw new RuntimeException("Bad request.");
        }

        return authorizationProvider.getAuthorization(new UserPrincipal(username));
    }
}
