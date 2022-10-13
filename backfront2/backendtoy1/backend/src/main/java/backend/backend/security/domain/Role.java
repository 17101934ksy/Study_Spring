package backend.backend.security.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum Role {

    ADMIN("ROLE_ADIN", "관리자"),
    USER("ROLE_USER", "일반 사용자"),
    GUEST("GUEST", "게스트");

    private final String key;
    private final String title;
}
