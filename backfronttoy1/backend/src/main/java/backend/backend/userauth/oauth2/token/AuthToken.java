package backend.backend.userauth.oauth2.token;


import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;

import java.security.Key;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.*;
import static org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256;

@Slf4j
@RequiredArgsConstructor
public class AuthToken {

    @Getter
    private final String token;
    private final Key key;

    private static final String AUTHORITIES_KEY = "role";

    AuthToken(String id, Date expiry, Key key){
        this.key = key;
        this.token = createAuthToken(id, expiry);
    }

    AuthToken(String id, String role, Date expiry, Key key){
        this.key = key;
        this.token = createAuthToken(id, role, expiry);
    }

    private String createAuthToken(String id, Date expiry){
        return Jwts.builder()
                .setSubject(id)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    private String createAuthToken(String id, String role, Date expiry){
        return Jwts.builder()
                .setSubject(id)
                .claim(AUTHORITIES_KEY, role)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    /**
     * Token 검증
     * 토큰이 키로 서명이 되고, 파싱에 에러가 없으면
     * @return true
     */
    public boolean validate() {
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (SecurityException e) {
            log.info("타당하지 않은 JWT 서명입니다.");
        } catch (MalformedJwtException e) {
            log.info("타당하지 않은 JWT 토큰입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch(IllegalArgumentException e) {
            log.info("적절하지 못한 인자입니다.");
        }
        return null;
    }

    public Claims getExpiredTokenClaims() {
        try{
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        } catch (ExpiredJwtException e) {
            log.info("만료된 토큰");
            return e.getClaims();
        }
        log.info("기간이 유효한 토큰");
        return null;
    }

}
