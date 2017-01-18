package com.epartner.shop;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;

@Configuration
public class SuccessLoginEvent implements ApplicationListener<AbstractAuthenticationEvent> {

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
            if(event.getAuthentication().isAuthenticated()) {
//                if (!((AlephUser) event.getAuthentication().getPrincipal()).isEnabled()) {
//                    throw new DisabledException("Account disabled");
//                }
            }
    }
}