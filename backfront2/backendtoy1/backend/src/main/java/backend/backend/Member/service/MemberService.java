package backend.backend.Member.service;

import backend.backend.Member.domain.Member;
import backend.backend.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findByEmail(String email){

        Optional<Member> findMember = memberRepository.findByEmail(email);

        return findMember.orElseThrow(() -> new IllegalArgumentException());
    }
}
