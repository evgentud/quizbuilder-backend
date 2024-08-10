package dev.tudos.quizbuilder.core.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * Security configuration for the application.
 */
@Configuration
@ConditionalOnProperty(name = "security.disabled", havingValue = "false", matchIfMissing = true)
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final LogoutHandler keycloakLogoutHandler;

    public SecurityConfig(KeycloakLogoutHandler keycloakLogoutHandler) {
        this.keycloakLogoutHandler = keycloakLogoutHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(exceptionHandling -> {
            exceptionHandling.authenticationEntryPoint((request, response, exception) -> {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
            });
        });
        http.authorizeHttpRequests(authorizeHttpRequest -> authorizeHttpRequest
                .requestMatchers("/actuator/health", "/actuator/metrics", "/actuator/metrics/**",
                        "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/api/monitor/**").permitAll()
                .anyRequest().authenticated()
        );
        http.oauth2ResourceServer((oauth2) -> oauth2
                .jwt(Customizer.withDefaults()));
        http.oauth2Login(Customizer.withDefaults())
                .logout(logout -> logout.addLogoutHandler(keycloakLogoutHandler).logoutSuccessUrl("/"));
        return http.build();
    }


}
