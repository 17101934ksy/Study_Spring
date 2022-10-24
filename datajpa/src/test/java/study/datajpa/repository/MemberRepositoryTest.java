package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.domain.Member;
import study.datajpa.exception.NotMemberException;

import java.util.ArrayList;
import java.util.List;
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

    @Test
    public void 리스트_조회검증() throws Exception {
        //given
        List<Member> members = memberCreateAndSave();

        //when
        List<Member> allMembers = memberRepository.findAll();
        long memberCount = memberRepository.count();

        //then
        assertThat(allMembers.size()).isEqualTo(2);
        assertThat(memberCount).isEqualTo(2);

    }

    @Test
    public void 삭제_검증() throws Exception {
        //given
        List<Member> members = memberCreateAndSave();

        //when
        memberRepository.delete(members.get(0));
        long count = memberRepository.count();

        //then
        assertThat(count).isEqualTo(1);

    }


    private static Member createMember(String name) {
        Member member = Member
                .builder()
                .username(name)
                .build();

        return member;
    }

    private List<Member> memberCreateAndSave() {
        List<Member> allMembers = new ArrayList<>();

        Member member1 = createMember("ko");
        Member member2 = createMember("se");

        memberRepository.save(member1);
        memberRepository.save(member2);

        allMembers.add(member1);
        allMembers.add(member2);

        return allMembers;
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