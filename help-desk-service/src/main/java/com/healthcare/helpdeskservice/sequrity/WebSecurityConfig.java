package com.healthcare.helpdeskservice.sequrity;

import org.springframework.context.annotation.Bean;
import com.healthcare.helpdeskservice.constants.AppConstants;
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

                            .requestMatchers(HttpMethod.POST,"/help-desk/resource/add").hasAuthority(AppConstants.ROLE_ADMIN)
                            .requestMatchers(HttpMethod.PUT,"/help-desk/resource/update/{resourceId}").hasAuthority(AppConstants.ROLE_ADMIN)
                            .requestMatchers(HttpMethod.DELETE,"/help-desk/resource/delete/{resourceId}").hasAuthority(AppConstants.ROLE_ADMIN)
                            .requestMatchers(HttpMethod.GET,"/help-desk/resource/view-individual-resource/{resourceId}").permitAll()
                            .requestMatchers(HttpMethod.GET,"/help-desk/resource/view-all-resource").permitAll()
                            .requestMatchers(HttpMethod.POST,"/help-desk/resource/allocate/{doctorId}").hasAuthority(AppConstants.ROLE_ADMIN)
                            .requestMatchers(HttpMethod.GET,"/help-desk/resource/cancel-allocation/{resourceAllocationId}").hasAuthority(AppConstants.ROLE_ADMIN)
                            .anyRequest().authenticated();
                })
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}
