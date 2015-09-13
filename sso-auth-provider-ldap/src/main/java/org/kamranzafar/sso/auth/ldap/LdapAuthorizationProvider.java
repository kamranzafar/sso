package org.kamranzafar.sso.auth.ldap;

import org.kamranzafar.sso.auth.Authorization;
import org.kamranzafar.sso.auth.AuthorizationProvider;
import org.kamranzafar.sso.auth.Principal;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.query.LdapQueryBuilder;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamran on 12/08/15.
 */
public class LdapAuthorizationProvider extends BaseProvider implements AuthorizationProvider {
    private String groupSearchFilter = "memberUid";

    public List<Authorization> getAuthorization(Principal principal) {
        final List<Authorization> authorizations = new ArrayList<Authorization>();

        ldapTemplate.search(LdapQueryBuilder.query().where(groupSearchFilter).is(principal.getName()), new
                AttributesMapper<String>() {
                    public String mapFromAttributes(Attributes attributes) throws NamingException {
                        String cn = attributes.get("cn").get().toString();
                        authorizations.add(new LdapAuthorization("cn", cn));

                        return cn;
                    }
                });

        return authorizations;
    }

    public String getGroupSearchFilter() {
        return groupSearchFilter;
    }

    public void setGroupSearchFilter(String groupSearchFilter) {
        this.groupSearchFilter = groupSearchFilter;
    }
}
