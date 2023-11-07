package com.healthcare.userservice.security;

import com.healthcare.userservice.constants.AppConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager)
            throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->{
                    auth
                            .requestMatchers(HttpMethod.POST, "/users/patient/register", "/users/patient/login").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/users/patient/register", "/users/patient/login","/patient/profile").hasRole(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.GET, "/users/patient/view-info/{id}", "/users/patient/view-info-by-email/{email}","/patient/profile").hasRole(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.DELETE, "/users/patient/account-deleting").hasAnyRole(AppConstants.ROLE_PATIENT,AppConstants.ROLE_ADMIN)
                            .requestMatchers(HttpMethod.POST, "/users/admin/register").permitAll()
                            .requestMatchers(HttpMethod.POST, "/users/admin/login").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/users/admin/remove-access","/users/admin/remove-access").hasRole("SUPER_ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/users/admin/remove-user").hasRole("SUPER_ADMIN")
                            .anyRequest().authenticated();
                })
                .addFilter(new CustomAuthenticationFilter(authenticationManager))
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
