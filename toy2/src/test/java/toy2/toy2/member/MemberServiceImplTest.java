package toy2.toy2.member;

import org.junit.jupiter.api.Test;
import toy2.toy2.repository.MemberRepository;
import toy2.toy2.repository.MemoryMemberRepository;
import toy2.toy2.domain.Grade;
import toy2.toy2.domain.Member;
import toy2.toy2.service.member.MemberService;
import toy2.toy2.service.member.MemberServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

class MemberServiceImplTest {

    @Test
    void joinMember(){
        MemberRepository memberRepository = new MemoryMemberRepository();
        MemberService memberService = new MemberServiceImpl(memberRepository);

        Member member1 = new Member(1L, "kkk", Grade.VIP);
        Member member2 = new Member(2L, "kkk", Grade.VIP);
        Member member3 = new Member(3L, "kkk", Grade.VIP);

        memberService.join(member1);
        memberService.join(member2);
        memberService.join(member3);

        assertThat(memberService.sizeMember()).isEqualTo(3);
    }

    void removeMember(){
        MemberRepository memberRepository = new MemoryMemberRepository();
        MemberService memberService = new MemberServiceImpl(memberRepository);

        Member member1 = new Member(1L, "kkk", Grade.VIP);
        Member member2 = new Member(2L, "kkk", Grade.VIP);
        Member member3 = new Member(3L, "kkk", Grade.VIP);

        memberService.join(member1);
        memberService.join(member2);
        memberService.join(member3);

        memberService.remove(member1);
        memberService.remove(member2);

        assertThat(memberService.sizeMember()).isEqualTo(1);
    }
}