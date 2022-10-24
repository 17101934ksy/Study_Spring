package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.domain.Member;
import study.datajpa.exception.NotMemberException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    public void 멤버저장() throws Exception {
        //given
        Member member = createMember();
        //when
        Member saveMember = memberRepository.save(member);

        Optional<Member> findMember = findMemberOrThrowError(saveMember.getId());

        //then
        assertThat(findMember.get().getId()).isEqualTo(member.getId());
        assertThat(findMember.get().getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember.get()).isEqualTo(member);

    }

    @Test
    public void 멤버없음_에러() throws Exception {
        //given
        Member member = Member.builder().
                username("ko")
                .build();

        //when

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    findMemberOrThrowError(4L);
                }
        );

        Throwable exception = org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    Optional<Member> findMember = findMemberOrThrowError(4L);
                });

        //then
        assertEquals("member not found", exception.getMessage());
    }

    private Optional<Member> findMemberOrThrowError(long id) {
        return Optional.ofNullable(memberRepository.findById(id)
                .orElseThrow(() -> {throw new NotMemberException();}));
    }

    private static Member createMember() {
        Member member = Member
                .builder()
                .username("ko")
                .build();

        return member;
    }
}