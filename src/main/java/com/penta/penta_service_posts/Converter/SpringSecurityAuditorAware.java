package com.penta.penta_service_posts.Converter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.penta.penta_service_posts.domain.Users;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<Users> {
    @Override
    public Optional<Users> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof JwtAuthenticationToken jwtAuth && jwtAuth.isAuthenticated()) {
            Jwt jwt = jwtAuth.getToken();
            try {
                UUID userId = UUID.fromString(jwt.getClaim("sub"));
                String username = jwt.getClaim("preferred_username");
                Users user = new Users(userId, username);
                return Optional.of(user);
            } catch (Exception e) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }
}
