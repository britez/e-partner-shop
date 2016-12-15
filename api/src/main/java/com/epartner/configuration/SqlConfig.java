//package com.epartner.configuration;
//
//import org.h2.server.web.WebServlet;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
//import org.springframework.boot.context.embedded.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import javax.sql.DataSource;
//
///**
// * Created by MARTIN on 22/09/16.
// */
//
//@Configuration
//public class SqlConfig {
//
//    @Value("${spring.datasource.url}")
//    private String url;
//    @Value("${spring.datasource.username}")
//    private String userName;
//    @Value("${spring.datasource.driver-class-name}")
//    private String driverClassName;
//    @Value("${spring.datasource.password}")
//    private String password;
//
//
//    @Bean
//    public ServletRegistrationBean h2servletRegistration() {
//        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
//        registration.addUrlMappings("/console/*");
//        return registration;
//    }
//
//    @Bean()
//    public DataSource sqlDataSource(){
//        return (DataSource) DataSourceBuilder.create()
//                .url(url)
//                .username(userName)
//                .username(userName)
//                .password(password)
//                .driverClassName(driverClassName)
//                .build();
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate() {
//        JdbcTemplate template = new JdbcTemplate(sqlDataSource());
//        return template;
//    }
//
//
//
//}
