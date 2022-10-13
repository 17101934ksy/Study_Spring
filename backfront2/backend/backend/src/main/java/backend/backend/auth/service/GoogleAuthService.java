package backend.backend.auth.service;

import backend.backend.auth.client.ClientGoogle;
import backend.backend.auth.dto.AuthRequest;
import backend.backend.auth.dto.AuthResponse;
import backend.backend.auth.jwt.AuthToken;
import backend.backend.auth.jwt.AuthTokenProvider;
import backend.backend.member.domain.Member;
import backend.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {

    private final ClientGoogle clientGoogle;
    private final MemberRepository memberRepository;
    private final AuthTokenProvider authTokenProvider;

    @Transactional
    public AuthResponse login(AuthRequest authRequest){
        Member googleMember = clientGoogle.getUserData(authRequest.getAccessToken());
        String socialId = googleMember.getSocialId();

        Optional<Member> findMember = memberRepository.findBySocialId(socialId);
        AuthToken appToken = authTokenProvider.createUserAppToken(socialId);

        if (findMember.isEmpty()) {

            memberRepository.save(googleMember);

            return AuthResponse.builder()
                    .appToken(appToken.getToken())
                    .isNewMember(TRUE)
                    .build();
        }

        return AuthResponse.builder()
                .appToken(appToken.getToken())
                .isNewMember(FALSE)
                .build();
    }
}
