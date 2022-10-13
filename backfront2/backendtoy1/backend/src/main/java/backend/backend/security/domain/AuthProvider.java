package backend.backend.security.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthProvider {
    GOOGLE("구글"),
    NAVER("네이버"),
    KAKAO("카카오");

    private final String brand;
}
