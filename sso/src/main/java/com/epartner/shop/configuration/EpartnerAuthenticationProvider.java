package com.epartner.shop.configuration;

import com.epartner.shop.domain.User;
import com.epartner.shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by martin on 21/12/16.
 */
public class EpartnerAuthenticationProvider implements AuthenticationProvider {

    private  UserService userService;

    public EpartnerAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        Optional<User> user = this.userService.authentication(name, password);
        if(user.isPresent()){
            User current = user.get();
            return new UsernamePasswordAuthenticationToken(current.getUsername(), password, current.getRoles());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
