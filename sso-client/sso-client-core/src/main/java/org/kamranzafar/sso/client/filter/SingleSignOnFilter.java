package org.kamranzafar.sso.client.filter;

import org.apache.commons.lang3.StringUtils;
import org.kamranzafar.sso.client.SingleSignOnException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by kamran on 12/08/15.
 */
public class SingleSignOnFilter extends BaseFilter {
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null || httpSession.isNew()) {
            String token = httpServletRequest.getParameter(PARAM_TOKEN);
            String user = httpServletRequest.getParameter(PARAM_USER);

            if (!StringUtils.isEmpty(token)) {
                httpSession = httpServletRequest.getSession();
            } else {
                redirectToLogin(httpServletRequest, httpServletResponse);
                return;
            }

            httpSession.setAttribute(SESSION_SSO_TOKEN, token);
            httpSession.setAttribute(SESSION_SSO_USER, user);
        }

        //Verify token
        try {
            System.out.println("++++++++++++++++++++ SESSION COOKIE {token:" + httpSession.getAttribute
                    (SESSION_SSO_TOKEN) + ", user:" + httpSession.getAttribute(SESSION_SSO_USER) + "}");

            if (!verifyToken((String) httpSession.getAttribute(SESSION_SSO_TOKEN),
                    (String) httpSession.getAttribute(SESSION_SSO_USER))) {
                httpSession.invalidate();
                redirectToLogout(httpServletRequest, httpServletResponse);
                return;
            }
        } catch (Exception e) {
            throw new SingleSignOnException(e.getMessage(), e);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
