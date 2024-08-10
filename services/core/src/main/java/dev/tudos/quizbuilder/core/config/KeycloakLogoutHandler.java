package dev.tudos.quizbuilder.core.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Logout handler for Keycloak.
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "security.disabled", havingValue = "false", matchIfMissing = true)
public class KeycloakLogoutHandler implements LogoutHandler {
    private static final String LOGOUT_URL = "/protocol/openid-connect/logout";

    private final RestTemplate restTemplate;

    public KeycloakLogoutHandler() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication auth) {
        logoutFromKeycloak((OidcUser) auth.getPrincipal());
    }

    private void logoutFromKeycloak(OidcUser user) {
        String endSessionEndpoint = user.getIssuer() + LOGOUT_URL;
        UriComponentsBuilder builder = UriComponentsBuilder
          .fromUriString(endSessionEndpoint)
          .queryParam("id_token_hint", user.getIdToken().getTokenValue());

        ResponseEntity<String> logoutResponse = restTemplate.getForEntity(builder.toUriString(), String.class);
        if (logoutResponse.getStatusCode().is2xxSuccessful()) {
            log.debug("Successfully logged out from Keycloak");
        } else {
            log.error("Could not propagate logout to Keycloak");
        }
    }
}
