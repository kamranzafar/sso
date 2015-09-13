package org.kamranzafar.sso.auth;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kamran on 12/08/15.
 */
public interface AuthorizationProvider extends Serializable {
    List<Authorization> getAuthorization(Principal principal);
}
