package org.kamranzafar.sso.server.config;

import org.kamranzafar.sso.DefaultTokenRegistry;
import org.kamranzafar.sso.SSOTokenRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by kamran on 14/08/15.
 */
@Configuration
public class ApplicationConfig {
    @Bean
    public SSOTokenRegistry ssoTokenRegistry(){
        DefaultTokenRegistry defaultTokenRegistry = DefaultTokenRegistry.getInstance();

        //TODO: set properties


        return defaultTokenRegistry;
    }
}
