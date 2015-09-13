package org.kamranzafar.sso.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kamran on 12/08/15.
 */
@Controller
public class SingleSignOutController extends BaseController {
    @RequestMapping(value = "logout")
    public void signOut(@CookieValue(value = "token", defaultValue = "") String token,
                        HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        String url = request.getParameter(PARAM_URL);

        destroySession(token, response);

        response.sendRedirect("/?" + PARAM_URL + "=" + url);
    }
}
