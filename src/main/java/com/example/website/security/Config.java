//package com.example.website.security;
//
//
//import com.example.website.service.UsersService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//
//@Configuration
//public class Config extends WebSecurityConfiguration {
//
//    @Autowired
//    UsersService usersService;
//
//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//            auth.authenticationProvider(authenticationProvider());
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(usersService);
//        authProvider.setPasswordEncoder(encoder());
//        return authProvider;
//    }
//}
