package backend.backend.security.dto;

import backend.backend.Member.domain.Member;
import backend.backend.security.domain.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.transaction.NotSupportedException;
import java.util.Map;

import static backend.backend.security.domain.AuthProvider.*;
import static backend.backend.security.domain.Role.USER;

@Getter
@Builder
@AllArgsConstructor
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String name;
    private String email;
    private String nameAttributeKey;
    private AuthProvider authProvider;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) throws NotSupportedException {

        switch (registrationId.toLowerCase()){
            case "google":
                return ofGoogle(userNameAttributeName, attributes);
            case "naver":
                return ofNaver(userNameAttributeName, attributes);
            case "kakao":
                return ofKakao(userNameAttributeName, attributes);
            default:
                throw new NotSupportedException();
        }
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .attributes(attributes)
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .nameAttributeKey(userNameAttributeName)
                .authProvider(GOOGLE)
                .build();

    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String,Object> attributes) {
        Map<String,Object> response = (Map<String,Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .attributes(response)
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .nameAttributeKey(userNameAttributeName)
                .authProvider(NAVER)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String,Object> attributes) {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return OAuthAttributes.builder()
                .attributes(account)
                .name((String) profile.get("nickname"))
                .email((String) account.get("email"))
                .nameAttributeKey(userNameAttributeName)
                .authProvider(KAKAO)
                .build();
    }

    public Member toMemberEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .role(USER)
                .authProvider(authProvider)
                .build();
    }


}
