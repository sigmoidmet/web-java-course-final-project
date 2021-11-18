package com.example.final_project.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class GlobalSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final JwtSecurityConfigurer jwtSecurityConfigurer;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .userDetailsService(userDetailsService)
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/register", "/error", "/login", "/clients/password", "/products", "/cart",
                             "/clients/password/reset/request", "/static/reset_password.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(jwtSecurityConfigurer)
                .and()
                .csrf()
                .disable()
                .cors()
                .disable();
    }
}