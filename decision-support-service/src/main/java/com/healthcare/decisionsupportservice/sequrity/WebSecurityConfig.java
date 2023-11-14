package com.healthcare.decisionsupportservice.sequrity;

import org.springframework.context.annotation.Bean;
import com.healthcare.decisionsupportservice.constants.AppConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

                            .requestMatchers(HttpMethod.POST,"/decision-support/goal/create").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.GET,"/decision-support/goal/by-patient/{patientId}").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.PUT,"/decision-support/goal/update-progress/{goalId}").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.DELETE,"/decision-support/goal/delete/{goalId}").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.GET,"/decision-support/goal/details/{goalId}").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.GET,"/decision-support/goal/progress-data/{goalId}").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.POST,"/decision-support/goal/send-notification/{patientId}").hasAuthority(AppConstants.ROLE_ADMIN)
                            .requestMatchers(HttpMethod.POST,"/decision-support/goal/update-external-progress/{patientId}").hasAuthority(AppConstants.ROLE_PATIENT)
                            .anyRequest().authenticated();
                })
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}
