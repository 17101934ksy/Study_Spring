package backend.backend.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static backend.backend.entity.SocialType.GOOGLE;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final OAuth2ClientProperties properties;

    public final String REDIRECTURI = "{baseUrl}/login/oauth2/code/{registrationId}";
    @Value("${registration.google.client-id}") private String googleClientId;
    @Value("${registration.google.client-secret}") private String googleClientSecret;

    @Value("${registration.naver.client-id}") private String naverClientId;
    @Value("${registration.naver.client-secret}") private String naverClientSecret;

    @Value("${registration.kakao.client-id}") private String kakaoClientId;
    @Value("${registration.kakao.client-secret}") private String kakaoClientSecret;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .oauth2Login(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties properties) {

        List<ClientRegistration> registrations = properties.getRegistration().keySet().stream()
                .map(client -> getRegistration(properties, client))
                .filter(Objects::nonNull).collect(Collectors.toList());

        registrations.add(
                CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                        .clientId(kakaoClientId)
                        .clientSecret(kakaoClientSecret)
                        .jwkSetUri("")
                        .build());

        registrations.add(
                CustomOAuth2Provider.Naver.getBuilder("naver")
                        .clientId(naverClientId)
                        .clientSecret(naverClientSecret)
                        .jwkSetUri("")
                        .build());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration getRegistration(OAuth2ClientProperties properties, String socialType) {
        log.info("clientId = " + googleClientId);
        log.info("clientSecret  = " + googleClientSecret);

        if (socialType.equals(GOOGLE.getValue())) {
            OAuth2ClientProperties.Registration registration
                    = properties.getRegistration().get(GOOGLE.getValue());

            return CommonOAuth2Provider.GOOGLE.getBuilder(socialType)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .scope("email", "profile")
                    .build();
        }
        return null;
    }
}
