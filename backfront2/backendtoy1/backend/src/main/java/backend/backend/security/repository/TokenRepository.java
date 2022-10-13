package backend.backend.security.repository;

import backend.backend.security.domain.LogoutAccessToken;
import backend.backend.security.domain.LogoutRefreshToken;
import backend.backend.security.domain.RefreshToken;

public interface TokenRepository {

    void saveLogoutAccessToken(LogoutAccessToken logoutAccessToken);

    void saveLogoutRefreshToken(LogoutRefreshToken logoutRefreshToken);

    void saveRefreshToken(RefreshToken refreshToken);

    boolean existsLogoutAccessTokenById(String token);

    boolean existsLogoutRefreshTokenById(String token);

    boolean existsRefreshTokenById(String token);

    void deleteRefreshTokenById(String token);
}
