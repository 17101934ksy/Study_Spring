package backend.backend.security.service;

import backend.backend.Member.domain.Member;
import backend.backend.Member.repository.MemberRepository;
import backend.backend.security.dto.OAuth2UserImpl;
import backend.backend.security.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomOauth2Service extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @SneakyThrows
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member member = saveMember(attributes);

        return OAuth2UserImpl.of(member, attributes.getAttributes());
    }

    private Member saveMember(OAuthAttributes attributes) {
        Optional<Member> findMember = memberRepository.findByEmail(attributes.getEmail());
        Member member;

        if (findMember.isPresent()) return findMember.get();

        member = memberRepository.saveAndFlush(attributes.toMemberEntity());
        return member;
    }
}
