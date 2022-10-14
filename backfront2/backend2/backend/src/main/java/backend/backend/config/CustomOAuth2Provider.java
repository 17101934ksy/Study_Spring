package backend.backend.config;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistration.Builder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

public enum CustomOAuth2Provider {

    KAKAO {
        @Override
        protected Builder getBuilder(String registrationId) {
            ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_POST, DEFAULT_REDIRECT_URL);

            builder.scope("profile_nickname", "account_email");
            builder.authorizationUri("https://kauth.kakao.com/oauth/authorize");
            builder.tokenUri("https://kauth.kakao.com/oauth/token");
            builder.userInfoUri("https://kapi.kakao.com/v2/user/me");
            builder.userNameAttributeName("id");
            builder.clientName("Kakao");

            return builder;
        }
    },
    Naver {
        @Override
        protected Builder getBuilder(String registrationId) {
            ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_POST, DEFAULT_REDIRECT_URL);
            builder.scope("email", "profile");
            builder.authorizationUri("https://nid.naver.com/oauth2.0/authorize");
            builder.tokenUri("https://nid.naver.com/oauth2.0/token");
            builder.userInfoUri("https://openapi.naver.com/v1/nid/me");
            builder.userNameAttributeName("response");
            builder.clientName("Naver");

            return builder;
        }
    };

    private static final String DEFAULT_REDIRECT_URL = "{baseUrl}/{action}/oauth2/code/{registrationId}";

    protected final Builder getBuilder(String registrationId, ClientAuthenticationMethod method, String redirectUrl) {
        Builder builder = ClientRegistration.withRegistrationId(registrationId);
        builder.clientAuthenticationMethod(method);
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder.redirectUri(redirectUrl);
        return builder;
    }

    protected abstract Builder getBuilder(String registrationId);
}
