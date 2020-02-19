package com.fbytes.cursus.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.stream.Collectors;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    @Autowired
    UsersConfig usersConfig;

    private PasswordEncoder delegateEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/gdialog/**").hasRole("EXT_USER")
                .antMatchers("/actuator/**").hasRole("INT_USER")
                .antMatchers("/tst/**").hasRole("INT_USER")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        logger.info("Configuring users (" + usersConfig.getSecurity().size() + "):");
        usersConfig.getSecurity().entrySet().forEach(user -> {
            String rolesStr = user.getValue().getRole().stream().collect(Collectors.joining(","));
            logger.info("User: " + user.getKey() + " Roles: " + rolesStr);
            User.UserBuilder userBuilder = User.withUsername(user.getKey());
            manager.createUser(userBuilder.username(user.getKey()).password(user.getValue().getPassword()).roles(rolesStr).build());
        });
        return manager;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(delegateEncoder);
    }
}