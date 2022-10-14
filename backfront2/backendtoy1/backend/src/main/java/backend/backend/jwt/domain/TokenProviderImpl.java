package backend.backend.jwt.domain;

import backend.backend.security.dto.OAuth2UserImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class TokenProviderImpl implements TokenProvider {

    public static final String TOKEN_TYPE = "Bearer ";

    private final Key secretKey;
    private final long accessTokenExpirationTimeInMilliSeconds;
    private final long refreshTokenExpirationTimeInMilliSeconds;
    private final long reissueRefreshTokenTimeInMilliSeconds;

    public TokenProviderImpl(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.access-expiration-time}") long accessTokenExpirationTimeInMilliSeconds,
            @Value("${jwt.refresh-expiration-time}") long refreshTokenExpirationTimeInMilliSeconds,
            @Value("${jwt.reissue-refresh-time}") long reissueRefreshTokenTimeInMilliSeconds
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());;
        this.accessTokenExpirationTimeInMilliSeconds = accessTokenExpirationTimeInMilliSeconds;
        this.refreshTokenExpirationTimeInMilliSeconds = refreshTokenExpirationTimeInMilliSeconds;
        this.reissueRefreshTokenTimeInMilliSeconds = reissueRefreshTokenTimeInMilliSeconds;
    }

    @Override
    public String createAccessToken(Authentication authentication) {
        return createToken(authentication, accessTokenExpirationTimeInMilliSeconds);
    }

    @Override
    public String createRefreshToken(Authentication authentication){
        return createToken(authentication, refreshTokenExpirationTimeInMilliSeconds);
    }

    @Override
    public String getUserEmailFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    @Override
    public long getRemainMillSeconds(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.getTime() - (new Date()).getTime();
    }

    @Override
    public boolean isMoreThanReissuedTime(String token) {
        return getRemainMillSeconds(token) >= reissueRefreshTokenTimeInMilliSeconds;
    }

    @Override
    public boolean validateToken(String token) {
        return this.getClaims(token) != null;
    }

    @Override
    public String removeType(String token){
        return token.substring(TOKEN_TYPE.length());
    }

    private String createToken(Authentication authentication, long accessTokenExpirationTimeInMilliSeconds) {
        OAuth2UserImpl oAuth2User = (OAuth2UserImpl) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpirationTimeInMilliSeconds);

        return Jwts.builder()
                .setSubject(oAuth2User.getName())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    private Claims getClaims(String token) {
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e){
            log.info("Invalid JWT signature");
        } catch (MalformedJwtException e){
            log.info("Invalid JWT token");
        } catch (ExpiredJwtException e){
            log.info("Expired JWT token");
        } catch (UnsupportedJwtException e){
            log.info("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid");
        }
        return null;
    }
}


