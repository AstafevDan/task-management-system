package com.dan.taskmanagementsystem.config;

import com.dan.taskmanagementsystem.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.dan.taskmanagementsystem.entity.enums.Permission.*;
import static com.dan.taskmanagementsystem.entity.enums.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/api/v1/auth/**",
                                    "/v2/api-docs",
                                    "v3/api-docs",
                                    "v3/api-docs/**",
                                    "/swagger-resources",
                                    "/swagger-resources/**",
                                    "/configuration/ui",
                                    "/configuration/security",
                                    "/swagger-ui/**",
                                    "/webjars/**",
                                    "/swagger-ui.html")
                            .permitAll();

                   registry.requestMatchers("/api/v1/tasks/{id}/status", "/api/v1/tasks/filter", 
                   "/api/v1/comments/filter", "/api/v1/comments").hasAnyRole(ADMIN.name(), USER.name());
                   registry.requestMatchers(POST, "/api/v1/comments").hasAuthority(ADD_COMMENT.name());
                   registry.requestMatchers(POST, "/api/v1/comments/filter").hasAuthority(READ_COMMENTS.name());
                   registry.requestMatchers(PATCH, "/api/v1/tasks/{id}/status").hasAuthority(UPDATE_STATUS.name());

                    registry.requestMatchers("/api/v1/tasks", "/api/v1/tasks/**", 
                    "/api/v1/comments", "/api/v1/comments/filter")
                            .hasRole(ADMIN.name());
                    registry.requestMatchers(GET, "/api/v1/tasks/**").hasAuthority(READ_TASK.name());
                    registry.requestMatchers(POST, "/api/v1/tasks/filter").hasAuthority(READ_TASK.name());
                    registry.requestMatchers(POST, "/api/v1/tasks").hasAuthority(CREATE_TASK.name());
                    registry.requestMatchers(PUT, "/api/v1/tasks/**").hasAuthority(UPDATE_TASK.name());
                    registry.requestMatchers(DELETE, "/api/v1/tasks/**").hasAuthority(DELETE_TASK.name());
                    registry.requestMatchers(PATCH, "/api/v1/tasks/**").hasAuthority(UPDATE_STATUS.name());
                    registry.requestMatchers(PATCH, "/api/v1/tasks/**").hasAuthority(UPDATE_PRIORITY.name());
                    registry.requestMatchers(PATCH, "/api/v1/tasks/**").hasAuthority(ASSIGN_EXECUTOR.name());
                    registry.requestMatchers(POST, "/api/v1/comments").hasAuthority(ADD_COMMENT.name());
                    registry.requestMatchers(POST, "/api/v1/comments/filter").hasAuthority(READ_COMMENTS.name());

                    registry.anyRequest().authenticated();
                })
                .sessionManagement(sessionManagement -> {
                  sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> {
                    logout.logoutUrl("/api/v1/auth/logout");
                    logout.addLogoutHandler(logoutHandler);
                    logout.logoutSuccessHandler((request, response, authentication) -> {
                        SecurityContextHolder.clearContext();
                    });
                })
                .build();
    }
}
