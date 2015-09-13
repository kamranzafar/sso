package org.kamranzafar.sso.client.filter;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.kamranzafar.sso.SSOConstants;
import org.kamranzafar.sso.client.SSOClientConstants;
import org.kamranzafar.sso.client.SingleSignOnException;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by kamran on 13/08/15.
 */
public abstract class BaseFilter implements Filter, SSOClientConstants, SSOConstants {
    protected String ssoUrl;

    public String getSsoUrl() {
        return ssoUrl;
    }

    public void setSsoUrl(String ssoUrl) {
        this.ssoUrl = ssoUrl;
    }

    protected void redirectToLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        redirect(httpServletRequest, httpServletResponse, "");
    }

    protected void redirectToVerify(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        redirect(httpServletRequest, httpServletResponse, "/verify");
    }

    protected void redirectToLogout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        redirect(httpServletRequest, httpServletResponse, "/logout");
    }

    protected void redirect(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                            String path) {
        String url = httpServletRequest.getRequestURL().toString();
        URLCodec urlCodec = new URLCodec();

        try {
            String fromUrl = urlCodec.encode(url);

            httpServletResponse.sendRedirect(ssoUrl + path + "?url=" + fromUrl);
        } catch (Exception e) {
            throw new SingleSignOnException(e.getMessage(), e);
        }
    }

    protected boolean verifyToken(String token, String user) throws Exception {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(user)) {
            return false;
        }

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(ssoUrl + "/verify/" + token + "/" + user);

        // add request header
        request.addHeader("User-Agent", CLIENT_USER_AGENT);
        HttpResponse response = client.execute(request);

        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println("+++++++++++++++++++ VERIFY result: " + result);

        if ("true".equals(result.toString().trim())) {
            System.out.println("+++++++++++++++++++ VERIFIED");
            return true;
        }

        return false;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        if (StringUtils.isEmpty(ssoUrl)) {
            ssoUrl = filterConfig.getInitParameter("sso-url");
        }
    }

    public void destroy() {
    }
}
