package capstonv1.capstonv1.api.member.application.service;

import capstonv1.capstonv1.api.member.domain.Member;
import capstonv1.capstonv1.api.member.infra.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member getMember(String socialId) {
        return memberRepository.findBySocialId(socialId);
    }
}
