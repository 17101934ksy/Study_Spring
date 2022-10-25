package capstonv1.capstonv1.oauth.application.service;

import capstonv1.capstonv1.api.member.domain.Member;
import capstonv1.capstonv1.api.member.infra.MemberRepository;
import capstonv1.capstonv1.oauth.domain.role.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String socialId) throws UsernameNotFoundException {

        Member member = memberRepository.findBySocialId(socialId);
        if (member == null) {
            throw new UsernameNotFoundException("Can not find username.");
        }
        return MemberPrincipal.create(member);
    }
}
