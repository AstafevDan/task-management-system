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

                    registry.requestMatchers("/api/v1/user/**").hasAnyRole(ADMIN.name(), USER.name());
                    registry.requestMatchers(GET, "/api/v1/user/**").hasAnyAuthority(ADMIN_READ.name(), USER_READ.name());
                    registry.requestMatchers(POST, "/api/v1/user/**").hasAnyAuthority(ADMIN_CREATE.name(), USER_CREATE.name());
                    registry.requestMatchers(PUT, "/api/v1/user/**").hasAnyAuthority(ADMIN_UPDATE.name(), USER_UPDATE.name());
                    registry.requestMatchers(DELETE, "/api/v1/user/**").hasAnyAuthority(ADMIN_DELETE.name(), USER_DELETE.name());

                    registry.requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name());
                    registry.requestMatchers(GET, "/api/v1/admin/**").hasAuthority(ADMIN_READ.name());
                    registry.requestMatchers(POST, "/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name());
                    registry.requestMatchers(PUT, "/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name());
                    registry.requestMatchers(DELETE, "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name());

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
