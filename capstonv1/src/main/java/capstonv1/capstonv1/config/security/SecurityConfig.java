package capstonv1.capstonv1.config.security;

import capstonv1.capstonv1.api.member.infra.MemberRefreshTokenRepository;
import capstonv1.capstonv1.config.properties.AppProperties;
import capstonv1.capstonv1.config.properties.CorsProperties;
import capstonv1.capstonv1.oauth.application.service.CustomMemberDetailsService;
import capstonv1.capstonv1.oauth.application.service.CustomOAuth2MemberService;
import capstonv1.capstonv1.oauth.domain.token.AuthTokenProvider;
import capstonv1.capstonv1.oauth.infra.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import capstonv1.capstonv1.oauth.presentation.handler.TokenAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsProperties corsProperties;
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final CustomMemberDetailsService memberDetailsService;
    private final CustomOAuth2MemberService oAuth2MemberService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;


    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }
}
