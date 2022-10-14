package backend.backend.jwt.repository;

import backend.backend.jwt.domain.LogoutAccessToken;
import backend.backend.jwt.domain.LogoutRefreshToken;
import backend.backend.jwt.domain.RefreshToken;

public interface TokenRepository {

    void saveLogoutAccessToken(LogoutAccessToken logoutAccessToken);

    void saveLogoutRefreshToken(LogoutRefreshToken logoutRefreshToken);

    void saveRefreshToken(RefreshToken refreshToken);

    boolean existsLogoutAccessTokenById(String token);

    boolean existsLogoutRefreshTokenById(String token);

    boolean existsRefreshTokenById(String token);

    void deleteRefreshTokenById(String token);
}
