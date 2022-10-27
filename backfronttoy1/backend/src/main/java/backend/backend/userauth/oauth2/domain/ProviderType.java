package backend.backend.userauth.oauth2.domain;

import lombok.Getter;

@Getter
public enum ProviderType {
    GOOGLE,
    FACEBOOK,
    NAVER,
    KAKAKO,
    LOCAL;
}
