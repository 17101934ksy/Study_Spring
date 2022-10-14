package backend.backend.Member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SocialType {

    GOOGLE("GOOGLE"),
    NAVER("NAVER"),
    KAKAO("KAKAO");

    private final String ROLE_PREFIX = "ROLE_";
    private final String name;

    private String getRoleType() {
        return ROLE_PREFIX + name;
    }

    private String getValue() {
        return name;
    }

    public boolean isEquals(String authority) {
        return this.getRoleType().equals(authority);
    }

}
