package com.healthcare.schedulingservice.sequrity;

import org.springframework.context.annotation.Bean;
import com.healthcare.schedulingservice.constants.AppConstants;
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
                            .requestMatchers(HttpMethod.POST,"/schedule/add").hasAuthority(AppConstants.ROLE_DOCTOR)
                            .requestMatchers(HttpMethod.POST,"/schedule/book-appointment/{scheduleId}").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.DELETE,"/schedule/cancel-appointment/{scheduleId}").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.GET,"/schedule/change-status/{appointmentId}").hasAuthority(AppConstants.ROLE_DOCTOR)
                            .requestMatchers(HttpMethod.GET,"/schedule/doctor/view-appointment").hasAuthority(AppConstants.ROLE_DOCTOR)
                            .requestMatchers(HttpMethod.GET,"/schedule/patient/view-appointment").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.GET,"/schedule/view-all-schedule/{doctorId}").permitAll()
                            .requestMatchers(HttpMethod.GET,"/schedule/view-schedule/{doctorId}").permitAll()
                            .requestMatchers(HttpMethod.GET,"/schedule/add-with-shift").hasAuthority(AppConstants.ROLE_DOCTOR)

                            .requestMatchers(HttpMethod.POST,"/schedule/shift").hasAuthority(AppConstants.ROLE_ADMIN)
                            .requestMatchers(HttpMethod.DELETE,"/schedule/shift/{shiftId}").hasAuthority(AppConstants.ROLE_ADMIN)
                            .requestMatchers(HttpMethod.PUT,"/schedule/shift/{shiftId}").hasAuthority(AppConstants.ROLE_ADMIN)
                            .requestMatchers(HttpMethod.GET,"/schedule/shift/{shiftId}").hasAuthority(AppConstants.ROLE_ADMIN)
                            .requestMatchers(HttpMethod.GET,"/schedule/shift").hasAuthority(AppConstants.ROLE_ADMIN)
                            .anyRequest().authenticated();
                })
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}
