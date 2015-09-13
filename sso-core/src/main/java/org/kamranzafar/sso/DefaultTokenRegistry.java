package org.kamranzafar.sso;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by kamran on 12/08/15.
 */
public class DefaultTokenRegistry implements SSOTokenRegistry {
    private static final DefaultTokenRegistry DEFAULT_TOKEN_REGISTRY = new DefaultTokenRegistry();
    private final Map<String, SSOToken> userTokenRegistry = new HashMap<String, SSOToken>();
    private final Map<String, SSOToken> tokenRegistry = new HashMap<String, SSOToken>();

    private DefaultTokenRegistry() {
    }

    public static DefaultTokenRegistry getInstance() {
        return DEFAULT_TOKEN_REGISTRY;
    }

    public SSOToken lookupUserToken(String username) {
        return userTokenRegistry.get(username);
    }

    @Override
    public SSOToken lookup(String token) {
        return tokenRegistry.get(token);
    }

    @Override
    public SSOToken register(String username) {
        String tokenStr = DigestUtils.shaHex(UUID.randomUUID().toString() + System.currentTimeMillis());

        SSOToken token = new SSOToken();
        token.setToken(tokenStr);
        token.setUsername(username);
        token.setUid(buildUid(token));

        tokenRegistry.put(token.getToken(), token);
        userTokenRegistry.put(username, token);

        return token;
    }

    @Override
    public void unregister(String token) {
        SSOToken ssoToken = tokenRegistry.get(token);

        if (ssoToken != null) {
            userTokenRegistry.remove(ssoToken.getUsername());
            tokenRegistry.remove(ssoToken.getToken());
        }
    }

    public boolean hasToken(String token) {
        return tokenRegistry.containsKey(token);
    }

    @Override
    public boolean validate(String token, String userHash) {
        String userShaHash = DigestUtils.shaHex(tokenRegistry.get(token).getUsername() + token);

        return userShaHash.equals(userHash);
    }

    public String buildUid(SSOToken token) {
        return buildUid(token.getToken(), token.getUsername());
    }

    @Override
    public String buildUid(String token, String username) {
        return DigestUtils.shaHex(username + token);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
