package com.digdes.school.project.security;

import com.digdes.school.project.enums.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfigurerImpl{

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.csrf().disable().httpBasic(Customizer.withDefaults())
                .authorizeRequests().requestMatchers(HttpMethod.POST, "/api/empl/create", "/api/project/create", "/api/task/create"
                        , "/api/project/*/team/add").hasAnyRole("USER")
                .and()
                .authorizeRequests().requestMatchers(HttpMethod.PUT, "/api/empl/*"
                        , "/api/project/*", "/api/project/status/*", "/api/task/*", "/api/task/status/*").hasAnyRole(UserRole.USER.toString())
                .and().authorizeRequests().requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole(UserRole.ADMINISTRATOR.toString(), UserRole.USER.toString())
                .and().authorizeRequests().requestMatchers(HttpMethod.DELETE, "/api/**").hasAnyRole(UserRole.ADMINISTRATOR.toString())
                .and().authorizeRequests().requestMatchers( "/api/auth/signup", "/swagger-ui.html/**", "/swagger-resources/**", "/v2/api-docs")
                .anonymous().anyRequest().authenticated()
                .and()
                .build();
    }



}