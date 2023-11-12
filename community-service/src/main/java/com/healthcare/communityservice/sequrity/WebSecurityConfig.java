package com.healthcare.communityservice.sequrity;

import com.healthcare.communityservice.constants.AppConstants;
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

                            .requestMatchers(HttpMethod.POST,"/community/groups/create").hasAuthority(AppConstants.ROLE_ADMIN)
                            .requestMatchers(HttpMethod.POST,"/community/groups/join/{groupId}").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.POST,"/community/posts/{groupId}").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.POST,"/community/like/{postId}").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.POST,"/community/comment/{postId}").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.POST,"/community/follower/{followerUserId}").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.DELETE,"/community/unfollow/{followerUserId}").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.DELETE,"/community/delete-post/{postId}").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.GET,"/community/get-follower").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.GET,"/community/post-info/{postId}").hasAuthority(AppConstants.ROLE_PATIENT)
                            .requestMatchers(HttpMethod.GET,"/community/group-posts/{groupId}").hasAuthority(AppConstants.ROLE_PATIENT)
                            .anyRequest().authenticated();
                })
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}
