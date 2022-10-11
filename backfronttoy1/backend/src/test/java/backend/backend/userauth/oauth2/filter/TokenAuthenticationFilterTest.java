package backend.backend.userauth.oauth2.filter;

import backend.backend.userauth.oauth2.exception.TokenValidFailedException;
import backend.backend.userauth.oauth2.token.AuthToken;
import backend.backend.userauth.oauth2.token.AuthTokenProvider;
import backend.backend.userauth.utils.HeaderUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
class TokenAuthenticationFilterTest extends Mockito {

    static HeaderUtil headerUtil;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    private final static String SECREY_KEY = "klgwelgkwngkw3fwelfwekflwganweit23r2";
    private final static String SUB = "testcodeonetwo";

    static AuthToken authToken;
    static AuthTokenProvider authTokenProvider;
    static TokenAuthenticationFilter tokenAuthenticationFilter;


    public boolean filterTest(AuthToken token) {

        if (token.validate()){

            try {
                Authentication authentication = authTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (TokenValidFailedException e) {
                throw new RuntimeException(e);
            }

            return true;
        }
        return false;
    }

    @Test
    public void 토큰_권한_필터_예외_처리() throws Exception {

        //given

        //만료시간 생성
        Date expiry = new Date();
        expiry.setTime(expiry.getTime() + 4000000000L);

        // 시크릿크에 의한 authTokenProvider 생성
        authTokenProvider = new AuthTokenProvider(SECREY_KEY);

        // 토큰 인증 필터 생성자
        tokenAuthenticationFilter = new TokenAuthenticationFilter(authTokenProvider);

        // authTokenProvider가 토큰을 소유한 사람에게 액세스 권한을 부여하는 Bearer에 토큰생성자가 만든 토큰을 입력해서 보냄
        AuthToken authTokenByProvider = authTokenProvider.createAuthToken(SUB, "", expiry);
        
        // HeaderUtil 생성 
        HeaderUtil headerUtil = new HeaderUtil();

        // request를 보내기 위한 가짜 request 생성
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(HEADER_AUTHORIZATION)).thenReturn(TOKEN_PREFIX + authTokenByProvider.getToken());

        // 토큰 권한 필터는 authTokenProvider를 파라미터로 생성
        tokenAuthenticationFilter = new TokenAuthenticationFilter(authTokenProvider);

        //when
        String tokenStr = headerUtil.getAccessToken(request); //-> HeaderUtil에서 검증된 테스트 코드
        AuthToken token = authTokenProvider.convertAuthToken(tokenStr); // 로직 검증 Bearer로 요청하여 프로바이더로 토큰 생성

        System.out.println("tokenStr = " + tokenStr);
        System.out.println("token.getToken() = " + token.getToken());
        System.out.println("token.getTokenClaims() = " + token.getTokenClaims());

        RuntimeException e = org.junit.jupiter.api.
                Assertions.assertThrows(RuntimeException.class,
                () -> filterTest(token));

        //then
        Assertions.assertThat(e.getMessage()).isEqualTo("A granted authority textual representation is required");

    }

    @Test
    public void 토큰_권한_필터_인가_승인() throws Exception {

        //given

        //만료시간 생성
        Date expiry = new Date();
        expiry.setTime(expiry.getTime() + 4000000000L);

        // 시크릿크에 의한 authTokenProvider 생성
        authTokenProvider = new AuthTokenProvider(SECREY_KEY);

        // 토큰 인증 필터 생성자
        tokenAuthenticationFilter = new TokenAuthenticationFilter(authTokenProvider);

        // authTokenProvider가 토큰을 소유한 사람에게 액세스 권한을 부여하는 Bearer에 토큰생성자가 만든 토큰을 입력해서 보냄
        AuthToken authTokenByProvider = authTokenProvider.createAuthToken(SUB, "ROLE_ADMIN", expiry);

        // HeaderUtil 생성
        HeaderUtil headerUtil = new HeaderUtil();

        // request를 보내기 위한 가짜 request 생성
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(HEADER_AUTHORIZATION)).thenReturn(TOKEN_PREFIX + authTokenByProvider.getToken());

        // 토큰 권한 필터는 authTokenProvider를 파라미터로 생성
        tokenAuthenticationFilter = new TokenAuthenticationFilter(authTokenProvider);

        //when
        String tokenStr = headerUtil.getAccessToken(request); //-> HeaderUtil에서 검증된 테스트 코드
        AuthToken token = authTokenProvider.convertAuthToken(tokenStr); // 로직 검증 Bearer로 요청하여 프로바이더로 토큰 생성

        //then
        Assertions.assertThat(filterTest(token)).isTrue();

    }

}