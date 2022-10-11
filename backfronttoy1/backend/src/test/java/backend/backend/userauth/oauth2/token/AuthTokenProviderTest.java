package backend.backend.userauth.oauth2.token;

import backend.backend.userauth.oauth2.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AuthTokenProviderTest {

    static AuthTokenProvider authTokenProvider;
    static final String hashKey = "kfwewefafwejfkjwenfjkln23wlalwekjfwek";
    static final String sub = "authTokenTest";

    static final Key key = Keys.hmacShaKeyFor((hashKey).getBytes());

    @Test
    public void createAuthToken_토큰생성_by_키_and_유효한_만료시간() throws Exception {
        //given

        Date now = new Date();
        now.setTime(now.getTime() + 403000000L);

        //when
        authTokenProvider = new AuthTokenProvider(hashKey);
        AuthToken authToken = authTokenProvider.createAuthToken(sub, now);

        //then
        assertThat(authToken.validate()).isTrue();
        assertThat(authToken.getExpiredTokenClaims()).isNull();
    }

    @Test
    public void convertAuthToken_토큰변경_타당하지않은_토큰() throws Exception {
        //given
        authTokenProvider = new AuthTokenProvider(hashKey);
        Date now = new Date();
        now.setTime(now.getTime() + 403000000L);

        //when
        String newHash = "rrregkleknlagkwoewnfwlefawefw.eweklwehviofawe.e2eklfwelek";

        AuthToken authToken = new AuthToken(sub, now, key);
        AuthToken newAuthToken = authTokenProvider.convertAuthToken(newHash);

        //then
        assertThat(newAuthToken.getTokenClaims()).isNull();
        assertThat(newAuthToken.validate()).isFalse();
    }

    @Test
    public void convertAuthToken_토큰변경_기간이_유효하지않은_토큰() throws Exception {
        //given
        authTokenProvider = new AuthTokenProvider(hashKey);
        Date now = new Date();

        //when
        AuthToken authToken = authTokenProvider.createAuthToken(sub + "test1", now);
        AuthToken newAuthToken = authTokenProvider.convertAuthToken(authToken.getToken());

        //then
        assertThat(newAuthToken.getTokenClaims()).isNull();
        assertThat(newAuthToken.validate()).isFalse();
    }

    @Test
    public void convertAuthToken_토큰변경_기간이_유효한_토큰() throws Exception {
        //given
        authTokenProvider = new AuthTokenProvider(hashKey);
        Date now = new Date();
        now.setTime(now.getTime() + 403000000L);

        //when
        AuthToken authToken = authTokenProvider.createAuthToken(sub + "test1", now);
        AuthToken newAuthToken = authTokenProvider.convertAuthToken(authToken.getToken());

        //then
        assertThat(newAuthToken.validate()).isTrue();
        assertThat(newAuthToken.getExpiredTokenClaims()).isNull();
    }

    @Test
    public void getAuthentication_권한_승인() throws Exception {
        //given

        authTokenProvider = new AuthTokenProvider(hashKey);
        Date expiry = new Date();
        expiry.setTime(expiry.getTime() + 40000000L);

        AuthToken authToken = authTokenProvider.createAuthToken(sub, "ROLE_USER", expiry);

        //when
        Authentication authentication = authTokenProvider.getAuthentication(authToken);
        
        Claims claims = authToken.getTokenClaims();
        List<SimpleGrantedAuthority> simpleGrantedAuthorityArray = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        simpleGrantedAuthorityArray.add(simpleGrantedAuthority);
        
        //then
        assertThat(claims.getSubject()).isEqualTo(sub);
        assertThat(authToken.validate()).isTrue();
        assertThat(authentication.getAuthorities()).isEqualTo(simpleGrantedAuthorityArray);
    }
    
    @Test
    public void getAuthentication_권한_승인_예외() throws Exception {
        //given
        authTokenProvider = new AuthTokenProvider(hashKey);
        Date expiry = new Date();
        expiry.setTime(expiry.getTime());

        //when
        AuthToken authToken = authTokenProvider.createAuthToken(sub, "ROLE_ADMIN", expiry);

        TokenValidFailedException e = org.junit.jupiter.api.Assertions.assertThrows(TokenValidFailedException.class,
                () -> authTokenProvider.getAuthentication(authToken));

        //then
        assertThat(e.getMessage()).isEqualTo("토큰 생성 실패");
    }
}