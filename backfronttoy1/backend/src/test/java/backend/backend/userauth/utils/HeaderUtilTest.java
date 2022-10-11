package backend.backend.userauth.utils;

import backend.backend.userauth.oauth2.token.AuthToken;
import backend.backend.userauth.oauth2.token.AuthTokenProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.http.HttpClient;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HeaderUtilTest extends Mockito {

    final static String secret_key = "efkwelfnwalfawelngwognowneofenf23ok";
    final static String sub = "efkwelfkwenfweo1234";
    final static String HEADER_AUTHORIZATION = "Authorization";
    final static String TOKEN_PREFIX = "Bearer ";

    static HeaderUtil headerUtil;
    static AuthTokenProvider authTokenProvider;
    static AuthToken authToken;
    static MockHttpServletRequest request;
    static MockHttpServletResponse response;

    @Test
    public void getAccessToken_헤더_null() throws Exception {
        //given
        Date now = new Date();
        now.setTime(now.getTime() + 40000000000L);
        headerUtil = new HeaderUtil();
        authTokenProvider = new AuthTokenProvider(secret_key);

        MockHttpServletRequest request = new MockHttpServletRequest();

        //when
        AuthToken authTokenByProvider = authTokenProvider.createAuthToken(sub, "ROLE_ADMIN", now);
        request.setAttribute("token", authTokenByProvider.getToken());
        String accessToken = headerUtil.getAccessToken(request);

        //then
        System.out.println("======================================================================");
        System.out.println("request.getHeader() = " + request.getHeader(HEADER_AUTHORIZATION));
        System.out.println("request.getAttribute(\"token\") = " + request.getAttribute("token"));
        System.out.println("======================================================================");

        assertThat(request.getHeader(HEADER_AUTHORIZATION)).isNull();
        assertThat(request.getAttribute("token")).isEqualTo((String) authTokenByProvider.getToken());
        assertThat(accessToken).isNull();
    }

    @Test
    public void getAccessToken_성공() throws Exception {
        //given
        Date now = new Date();
        now.setTime(now.getTime() + 40000000000L);
        headerUtil = new HeaderUtil();
        authTokenProvider = new AuthTokenProvider(secret_key);

        MockHttpServletRequest request = new MockHttpServletRequest();


//        Map<String, String> headers = new HashMap<>();
//        headers.put(null, "HTTP/1.1 200 OK");
//        headers.put("Content-Type", "application/json");
//        headers.put("Authorization", "authTokenProviderSeyun");
//        Enumeration<String> headerNames = Collections.enumeration(headers.keySet());

        HttpServletRequest requestByHeader = mock(HttpServletRequest.class);
//        when(requestByHeader.getHeaderNames()).thenReturn(headerNames);
        when(requestByHeader.getHeader("Authorization")).thenReturn("Bearer seyun");
        
        //when
        AuthToken authTokenByProvider = authTokenProvider.createAuthToken(sub, "ROLE_ADMIN", now);
//        request.setAttribute("token", authTokenByProvider.getToken());
//        request.setAttribute("Header", HEADER_AUTHORIZATION);
        String accessToken = headerUtil.getAccessToken(requestByHeader);


        //then
        System.out.println("========================================");
        System.out.println("request.getHeader(\"Header\") = " + request.getHeader("Header"));
        System.out.println("requestByHeader.getHeader(HEADER_AUTHORIZATION) = " + requestByHeader.getHeader(HEADER_AUTHORIZATION));
        System.out.println("========================================");
        System.out.println("accessToken = " + accessToken);

        assertThat(accessToken).isEqualTo("seyun");
    }
}