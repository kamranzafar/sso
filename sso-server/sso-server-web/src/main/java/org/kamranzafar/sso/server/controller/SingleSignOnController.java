package org.kamranzafar.sso.server.controller;

import org.kamranzafar.sso.auth.*;
import org.kamranzafar.sso.server.LoginRequest;
import org.kamranzafar.sso.SSOToken;
import org.kamranzafar.sso.DefaultTokenRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kamran on 12/08/15.
 */
@Controller
public class SingleSignOnController extends BaseController {
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @RequestMapping("/")
    public String loginHome(@CookieValue(value = "token", defaultValue = "") String token, @CookieValue(value =
            "uid", defaultValue = "") String uid, Model model, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        String url = request.getParameter(PARAM_URL);
        SSOToken ssoToken = DefaultTokenRegistry.getInstance().lookup(token);

        System.out.println("++++++++++++++++++++ URL {" + url + "} - COOKIE {token:" + token + ", uid:" + uid + "} - "
                + "SSO_TOKEN {" + ssoToken + "}");

        if (!StringUtils.isEmpty(token) && ssoToken != null) {
            if (DefaultTokenRegistry.getInstance().validate(ssoToken.getToken(), uid)) {
                response.sendRedirect(url + "?" + PARAM_TOKEN + "=" + ssoToken.getToken()
                        + "&" + PARAM_USER + "=" + ssoToken.getUsername());
            } else {
                destroySession(token, response);
            }
        }

        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("Bad request.");
        }

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUrl(url);

        model.addAttribute("login", loginRequest);

        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public void signOn(@ModelAttribute LoginRequest loginRequest, HttpServletResponse response) throws Exception {
        String url = loginRequest.getUrl();

        System.out.println("++++++++++++++++++++ " + loginRequest.getUsername() + " {" + url + "}");

        UserPrincipal userPrincipal = new UserPrincipal(loginRequest.getUsername());
        UserCredential userCredential = new UserCredential(loginRequest.getPassword());

        Authentication authResult = authenticationProvider.authenticate(new UserAuthentication(userPrincipal,
                userCredential));

        SSOToken token = initializeSession(loginRequest, response);

        response.sendRedirect(url + "?" + PARAM_TOKEN + "=" + token.getToken()
                + "&" + PARAM_USER + "=" + token.getUsername());
    }
}
