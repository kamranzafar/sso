package org.kamranzafar.sso;

import java.util.Properties;

/**
 * Created by kamran on 14/08/15.
 */
public interface SSOTokenRegistry {
    SSOToken register(String user);
    void unregister(String token);
    SSOToken lookup(String token);
    boolean validate(String token, String user);
    String buildUid(String token, String user);
    void setProperties(Properties properties);
}
