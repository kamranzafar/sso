package org.kamranzafar.sso;

import java.io.Serializable;

/**
 * Created by kamran on 12/08/15.
 */
public class SSOToken implements Serializable {
    private String username;
    private String uid;
    private String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
