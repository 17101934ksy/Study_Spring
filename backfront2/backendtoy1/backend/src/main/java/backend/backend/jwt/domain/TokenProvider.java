package backend.backend.jwt.domain;

import org.springframework.security.core.Authentication;

public interface TokenProvider {
    String TOKEN_TYPE = "Bearer ";

    String createAccessToken(Authentication authentication);

    String createRefreshToken(Authentication authentication);

    String getUserEmailFromToken(String token);

    long getRemainMillSeconds(String token);

    boolean isMoreThanReissuedTime(String token);

    boolean validateToken(String token);

    String removeType (String token);
}
