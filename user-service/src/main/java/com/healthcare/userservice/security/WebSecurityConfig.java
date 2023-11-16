package com.healthcare.userservice.security;

import com.healthcare.userservice.constants.AppConstants;
import org.springframework.context.annotation.Bean;
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
                            .requestMatchers(HttpMethod.POST, AppConstants.SIGN_IN,AppConstants.SIGN_UP).permitAll()
                            .requestMatchers(HttpMethod.GET, "/users/register/doctor").hasAuthority(AppConstants.ROLE_ADMIN)
                            .requestMatchers(HttpMethod.GET, "/users/doctor/profile").hasAuthority(AppConstants.ROLE_DOCTOR)
                            .requestMatchers(HttpMethod.GET, "/users/doctor/profile/{doctorId}").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/users/doctor/update-profile").hasAuthority(AppConstants.ROLE_DOCTOR)
                            .requestMatchers(HttpMethod.PUT, "/users/patient/update-profile").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.GET, "/users/patient/profile").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.POST, "/users/add/health-data").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.GET, "/users/health-data").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.GET, "/users/health-data/{userId}").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.GET, "/users/health-data/all").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.POST, "/users/fileSystem").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.POST, "/users/fileSystem/doctor").hasAuthority(AppConstants.ROLE_DOCTOR)
                            .requestMatchers(HttpMethod.GET, "/users/fileSystem/{id}").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/users/fileSystem/delete/{id}").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.DELETE, "/users/fileSystem/doctor/delete/{id}").hasAuthority(AppConstants.ROLE_DOCTOR)
                            .requestMatchers(HttpMethod.PUT, "/users/doctor/update-allocation/{doctorId}").hasAuthority(AppConstants.ROLE_ADMIN)


                            .requestMatchers(HttpMethod.GET, "/users/doctor/by-name/{doctorName}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/users/doctor/by-specialities/{speciality}").permitAll()
                            .anyRequest().authenticated();
                })
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}
