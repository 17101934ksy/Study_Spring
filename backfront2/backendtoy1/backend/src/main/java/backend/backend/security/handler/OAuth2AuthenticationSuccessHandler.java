package backend.backend.security.handler;

import backend.backend.security.domain.RefreshToken;
import backend.backend.security.domain.TokenProvider;
import backend.backend.security.repository.TokenRepository;
import backend.backend.security.dto.TokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;
    private final TokenRepository tokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String refreshToken = tokenProvider.createRefreshToken(authentication);
        String accessToken = tokenProvider.createAccessToken(authentication);
        TokenResponse tokenResponse = TokenResponse.of(accessToken, refreshToken);

//        tokenRepository.saveRefreshToken(
//                RefreshToken.of(refreshToken, tokenProvider.getRemainMillSeconds(refreshToken))
//        );
        System.out.println("===================================================================");
        System.out.println("tokenResponse.getAccessToken() = " + tokenResponse.getAccessToken());
        System.out.println("===================================================================");
        log.info("토큰발급 완료");

        objectMapper.writeValue(response.getWriter(), tokenResponse);
        log.info("리프레시 토큰 발급 ");

    }
}
