package backend.backend.auth.client;

import backend.backend.auth.dto.GoogleUserResponse;
import backend.backend.auth.exception.TokenValidFailedException;
import backend.backend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static backend.backend.auth.enumerate.RoleType.*;
import static backend.backend.member.enumerate.MemberProvider.*;

@Component
@RequiredArgsConstructor
public class ClientGoogle implements ClientProxy {

    private final WebClient webClient;

    @Override
    public Member getUserData(String accessToken) {
        GoogleUserResponse googleUserReponse = webClient.get()
                .uri("https://oauth2.googleapis.com/tokeninfo", builder -> builder.queryParam("id_token", accessToken).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidFailedException("소셜 로그인 엑세스 토큰 권한이 없습니다.")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new TokenValidFailedException("서버 내부 에러입니다.")))
                .bodyToMono(GoogleUserResponse.class)
                .block();

        return Member.builder()
                .socialId(googleUserReponse.getSub())
                .name(googleUserReponse.getName())
                .email(googleUserReponse.getEmail())
                .memberProvider(GOOGLE)
                .roleType(USER)
                .build();
    }
}
