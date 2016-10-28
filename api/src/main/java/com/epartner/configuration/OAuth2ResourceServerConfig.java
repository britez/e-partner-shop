package com.epartner.configuration;

import com.epartner.renderer.CustomDefaultOAuth2ExceptionRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by biandra on 25/08/15.
 */
@EnableResourceServer
@Configuration
@EnableWebSecurity
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private TokenExtractor tokenExtractor = new BearerTokenExtractor();

    @Value("${spring.auth.serverurl}")
    String tokenEndpointUrl;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.addFilterAfter(new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                // We don't want to allow access to a resource with no token so clear
                // the security context in case it is actually an OAuth2Authentication
                if (tokenExtractor.extract(request) == null) {

                    List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
                    auths.add(new SimpleGrantedAuthority("anon"));
                    SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken("anon", "anon", auths));
                }
                filterChain.doFilter(request, response);
            }
        }, AbstractPreAuthenticatedProcessingFilter.class);

        http
            .csrf()
            .disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**/tags", "/**/carousels", "/**/items/**")
                .permitAll()
            .and()
                .authorizeRequests()
                .anyRequest()
                    .authenticated();
    }

    @Bean
    public RemoteTokenServices remoteTokenServices() {
        final RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setCheckTokenEndpointUrl(tokenEndpointUrl);
        remoteTokenServices.setClientId("epartner-ui");
        remoteTokenServices.setClientSecret("epartner-ui");
        return remoteTokenServices;
    }

    @Value("${epartner.oauth.uri}")
    private String oauthHost;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        OAuth2AuthenticationEntryPoint ep = new OAuth2AuthenticationEntryPoint();
        ep.setExceptionRenderer(new CustomDefaultOAuth2ExceptionRenderer(oauthHost));
        resources.authenticationEntryPoint(ep);
    }

}
