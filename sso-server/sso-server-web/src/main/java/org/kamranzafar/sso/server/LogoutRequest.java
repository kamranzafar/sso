package org.kamranzafar.sso.server;

/**
 * Created by kamran on 12/08/15.
 */
public class LogoutRequest {
    private String token;
    private String url;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
