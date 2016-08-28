package com.epartner;

import com.epartner.configuration.OAuth2ResourceServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Created by biandra on 14/12/15.
 */
@SpringBootApplication
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
@Import({OAuth2ResourceServerConfig.class})
public class Application {

    public static void main(String[] args)  {
        SpringApplication.run(Application.class, args);
    }

}





