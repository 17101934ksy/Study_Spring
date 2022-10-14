package backend.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialType {
    GOOGLE("ROLE_GOOGLE"),
    KAKAO("ROLE_KAKAO");

    private String value;

    public boolean isEquals(String authority) {
        return this.getValue().equals(authority);
    }
}
