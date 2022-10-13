package backend.backend.auth.client;

import backend.backend.auth.dto.KakaoUserResponse;
import backend.backend.auth.enumerate.RoleType;
import backend.backend.auth.exception.TokenValidFailedException;
import backend.backend.member.domain.Member;
import backend.backend.member.enumerate.MemberProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static backend.backend.auth.enumerate.RoleType.USER;
import static backend.backend.member.enumerate.MemberProvider.KAKAO;

@Component
@RequiredArgsConstructor
public class ClientKakao implements ClientProxy{

    private WebClient webClient;

    @Override
    public Member getUserData(String accessToken) {
        KakaoUserResponse kakaoUserResponse = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidFailedException("소셜 로그인 엑세스 토큰 권한이 없습니다.")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new TokenValidFailedException("서버 내부 에러입니다.")))
                .bodyToMono(KakaoUserResponse.class)
                .block();

        return Member.builder()
                .socialId(String.valueOf(kakaoUserResponse.getId()))
                .name(String.valueOf(kakaoUserResponse.getProperties().getNickname()))
                .email(String.valueOf(kakaoUserResponse.getKakaoAccount().getEmail()))
                .memberProvider(KAKAO)
                .roleType(USER)
                .build();
    }
}
