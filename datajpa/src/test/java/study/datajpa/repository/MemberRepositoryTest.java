package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.domain.Member;
import study.datajpa.domain.Team;
import study.datajpa.exception.NotMemberException;
import study.datajpa.memberdto.MemberDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

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

    @Test
    public void findMembers_query() throws Exception {
        //given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 10);

        //when
        memberRepository.save(m1);
        memberRepository.save(m2);

        //then
        List<Member> result = memberRepository.findMembers("AAA", 10);
        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
    }

    @Test
    public void findUsernameList() throws Exception {
        //given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 10);

        //when
        memberRepository.save(m1);
        memberRepository.save(m2);

        //then
        Optional<List<String>> usernameList = memberRepository.findUsernameList();

        List<String> memberUsernames = usernameList.orElseThrow(() -> {
            throw new NotMemberException();
        });
        assertThat(memberUsernames.size()).isEqualTo(2);
    }

    @Test
    public void findMemberDto() throws Exception {
        //given
        Team t1 = new Team("team1");
        Team t2 = new Team("team2");

        teamRepository.save(t1);
        teamRepository.save(t2);
        teamRepository.flush();

        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 10);
        Member m3 = new Member("CCC", 30);
        m1.updateTeam(t1);
        m2.updateTeam(t2);

        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);

        //when
        Optional<List<MemberDto>> memberDto = memberRepository.findMemberDto();
        List<MemberDto> memberDtos = memberDto.orElseThrow(() -> {
            throw new NotMemberException();
        });

        //then
        assertThat(memberDtos.size()).isEqualTo(2);
    }

    @Test
    public void findNames() throws Exception {
        //given
        List<Member> members = memberCreateAndSave();
        Member member1 = new Member("name1", 15);
        memberRepository.save(member1);

        List<String> names = new ArrayList<>();

        for (Member member : members) {
            names.add(member.getUsername());
        }

        //when
        Optional<List<Member>> findMembersByNames = memberRepository.findByNames(names);
        List<Member> findMembers = findMembersByNames.orElseThrow(
                () -> {
                    throw new NotMemberException();
                });

        Optional<List<Member>> arraysFindNames = memberRepository.findByNames(Arrays.asList("ko", "se"));
        List<Member> arraysFindMembers = arraysFindNames.orElseThrow(
                () -> {
                    throw new NotMemberException();
                });


        //then
        assertThat(findMembers.size()).isEqualTo(2);
        assertThat(arraysFindMembers.size()).isEqualTo(2);
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