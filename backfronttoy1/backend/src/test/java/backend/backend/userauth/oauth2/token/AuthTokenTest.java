package backend.backend.userauth.oauth2.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.Key;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AuthTokenTest {

    static final String hashKey = "kfwewefafwejfkjwenfjkln23wlalwekjfwek";
    static final String sub = "authTokenTest";

    static final Key key = Keys.hmacShaKeyFor((hashKey).getBytes());

    @Test
    public void 생성자_토큰_생성_by_키_and_유효한_만료시간() throws Exception {
        //given

        Date now = new Date();
        now.setTime(now.getTime() + 2000000000L);
        AuthToken authToken = new AuthToken(sub, now, key);

        //when
        Claims getClaims = authToken.getTokenClaims();
        Claims expiredClaims = authToken.getExpiredTokenClaims();

        //then
        assertThat(getClaims.getSubject()).isEqualTo(sub);
        assertThat(authToken.validate()).isTrue();
        assertThat(expiredClaims).isNull();
    }

    @Test
    public void 생성자_토큰생성_by_키_and_만료된_시간() throws Exception {
        //given
        Date now = new Date();
        AuthToken authToken = new AuthToken(sub, now, key);

        //when
        Claims getClaims = authToken.getTokenClaims();
        Claims expiredClaims = authToken.getExpiredTokenClaims();

        //then
        // 만료된 토큰은 getTokenClaims == null
        assertThat(getClaims).isNull();
        assertThat(expiredClaims.getSubject()).isEqualTo(sub);

    }

}