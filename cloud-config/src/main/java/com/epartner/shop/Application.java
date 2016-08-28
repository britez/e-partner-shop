package com.epartner.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.SessionAttributes;

@SpringBootApplication
@SessionAttributes("authorizationRequest")
@EnableResourceServer
@EnableConfigServer
public class Application extends ResourceServerConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
            .csrf()
                .disable()

            .authorizeRequests()
                .anyRequest()
                    .permitAll()
            .and()

            .anonymous()
                .authorities("ROLE_ANONYMOUS");
    }

}