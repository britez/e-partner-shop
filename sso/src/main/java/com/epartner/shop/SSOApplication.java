package com.epartner.shop;

import com.epartner.shop.configuration.EpartnerAuthenticationProvider;
import com.epartner.shop.domain.Role;
import com.epartner.shop.domain.User;
import com.epartner.shop.repositories.RoleRepository;
import com.epartner.shop.repositories.UserRepository;
import com.epartner.shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.bind.annotation.SessionAttributes;

@SpringBootApplication
@SessionAttributes("authorizationRequest")
@EnableResourceServer
public class SSOApplication extends ResourceServerConfigurerAdapter {

    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";

    public static void main(String[] args) {
        SpringApplication.run(SSOApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(
            RoleRepository repository,
            UserRepository userRepository){
        return (args)->{
            Role role = new Role();
            role.setAuthority(ADMIN);
            repository.save(role);
            Role role2 = new Role();
            role2.setAuthority(USER);
            repository.save(role2);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            admin.addRole(role);
            userRepository.save(admin);

        };
    }




    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()

                .authorizeRequests()
                .antMatchers(
                        "/formUser",
                        "/user",
                        "/role",
                        "/role/**",
                        "/confirmation/**",
                        "/user/confirm/**",
                        "/user/forgot",
                        "/errorNoHash",
                        "/forgotPassword",
                        "/settings")
                .permitAll()
                .and()

                .csrf().disable()

                .authorizeRequests().anyRequest().authenticated()
                .and()

                .anonymous().authorities("ROLE_ANONYMOUS");
    }

    @Value("${epartner.oauth.uri}")
    private String oauthHost;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        OAuth2AuthenticationEntryPoint ep = new OAuth2AuthenticationEntryPoint();
        ep.setExceptionRenderer(new CustomDefaultOAuth2ExceptionRenderer(oauthHost));
        resources.authenticationEntryPoint(ep);
    }


    @Configuration
    @Order(-10)
    protected static class LoginConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER).and()

                    .csrf()
                    .disable()

                    .requestMatchers()
                    .antMatchers(
                            "/login",
                            "/oauth/authorize",
                            "/oauth/confirm_access",
                            "/oauth/logout")
                    .and()

                    .formLogin()
                    .loginPage("/login").failureUrl("/login?error=true").permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/oauth/logout")
                    .invalidateHttpSession(true)
                    .logoutSuccessHandler(new CustomLogoutSuccessHandler()).permitAll()
                    .and()
                    .authorizeRequests()
                    .anyRequest().authenticated();

        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.parentAuthenticationManager(authenticationManager);
        }

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class OAuth2AuthorizationConfig extends
            AuthorizationServerConfigurerAdapter {

        @Autowired
        Environment environment;

        @Bean
        public TokenStore tokenStore() {
            return new InMemoryTokenStore();
        }

        @Autowired
        private AuthenticationManager authenticationManager;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient("epartner-ui")
                    .secret("epartner-ui").scopes("home")
                    .autoApprove(true)
                    .authorizedGrantTypes("authorization_code", "implicit", "refresh_token", "password");
        }

        @Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setTokenStore(tokenStore());
            return tokenServices;
        }


        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {

            endpoints.tokenStore(tokenStore())
                    .authenticationManager(authenticationManager);

        }

        @Value("${epartner.oauth.uri}")
        private String oauthHost;

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer)
                throws Exception {
            OAuth2AuthenticationEntryPoint ep = new OAuth2AuthenticationEntryPoint();
            ep.setExceptionRenderer(new CustomDefaultOAuth2ExceptionRenderer(oauthHost));
            oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess(
                    "isAuthenticated()")
                    .authenticationEntryPoint(ep)
                    .allowFormAuthenticationForClients();
        }

    }


    @Configuration
    @EnableWebSecurity
    @Order(201)
    protected static class AuthServerConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserService service;

        @Autowired
        public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(getAuthProvider());
        }

        @Bean
        public AuthenticationProvider getAuthProvider() {
            return new EpartnerAuthenticationProvider(service);
        }
    }

}