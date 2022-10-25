package capstonv1.capstonv1.oauth.application.service;

import capstonv1.capstonv1.api.member.domain.Member;
import capstonv1.capstonv1.api.member.infra.MemberRepository;
import capstonv1.capstonv1.oauth.domain.info.OAuth2MemberInfo;
import capstonv1.capstonv1.oauth.domain.info.OAuth2MemberInfoFactory;
import capstonv1.capstonv1.oauth.domain.role.MemberPrincipal;
import capstonv1.capstonv1.oauth.domain.role.ProviderType;
import capstonv1.capstonv1.oauth.domain.role.RoleType;
import capstonv1.capstonv1.oauth.presentation.exception.OAuthProviderMissMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {

        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2MemberInfo memberInfo = OAuth2MemberInfoFactory.getOAuth2MemberInfo(providerType, user.getAttributes());
        Member findMember = memberRepository.findBySocialId(memberInfo.getId());


        if (findMember != null) {
            if (providerType != findMember.getProviderType()) {
                throw new OAuthProviderMissMatchException(
                        "Looks like you're signed up with " + providerType +
                                " account. Please use your " + findMember.getProviderType() + " account to login."
                );
            }
            updateUser(findMember, memberInfo);
        } else {
            findMember = createUser(memberInfo, providerType);
        }

        return MemberPrincipal.create(findMember, user.getAttributes());
    }

    private Member createUser(OAuth2MemberInfo memberInfo, ProviderType providerType) {
        LocalDateTime now = LocalDateTime.now();
        Member member = Member.builder()
                .socialId(memberInfo.getId())
                .email(memberInfo.getEmail())
                .username(memberInfo.getName())
                .roleType(RoleType.USER)
                .providerType(providerType)
                .build();

        return memberRepository.saveAndFlush(member);
    }

    private Member updateUser(Member member, OAuth2MemberInfo memberInfo) {
        if (memberInfo.getName() != null && !member.getUsername().equals(memberInfo.getName())) {
            member.updateUsername(memberInfo.getName());
        }

        return member;
    }


}
