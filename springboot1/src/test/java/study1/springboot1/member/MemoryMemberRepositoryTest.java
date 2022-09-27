package study1.springboot1.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryMemberRepositoryTest {

    @Test
    void saveAndFindByIdMember(){

        MemberRepository memberRepository = new MemoryMemberRepository();

        Member member1 = new Member(1L, "koseyun1", Grade.VIP);
        Member member2 = new Member(2L, "koseyun2", Grade.VIP);

        memberRepository.save(member1);
        memberRepository.save(member2);

        assertThat(memberRepository.findById(1L)).isEqualTo(member1);
        assertThat(memberRepository.findById(2L)).isEqualTo(member2);
    }

    @Test
    void removeMember(){

        MemberRepository memberRepository = new MemoryMemberRepository();

        Member member1 = new Member(1L, "koseyun", Grade.VIP);
        Member member2 = new Member(2L, "koseyun1", Grade.VIP);

        memberRepository.save(member1);
        memberRepository.save(member2);

        memberRepository.removeMember(1L);
        assertThat(memberRepository.findById(1L)).isNull();
    }

}