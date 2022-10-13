package backend.backend.security.domain;

import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("logoutAccessToken")
@NoArgsConstructor
public class LogoutAccessToken extends Token {

    private LogoutAccessToken(String id, long expiration) {
        super(id, expiration);
    }

    public static LogoutAccessToken of (String accessToken, long expiration){
        return new LogoutAccessToken(accessToken,expiration);
    }
}