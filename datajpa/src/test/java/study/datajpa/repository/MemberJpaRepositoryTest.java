package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.domain.Member;
import study.datajpa.exception.NotMemberException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    public void 멤버저장() throws Exception {
        //given
        Member member = createMember("ko");

        //when
        Member saveMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(saveMember.getId());

        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void 멤버저장_findById() throws Exception {
        //given
        List<Member> members = memberCreateAndSave();

        //when
        Member findMember1 = memberJpaRepository.findById(members.get(0).getId()).orElseThrow(
                () -> {throw new NotMemberException();});
        Member findMember2 = memberJpaRepository.findById(members.get(1).getId()).orElseThrow(
                () -> {throw new NotMemberException();});

        //then
        assertThat(findMember1).isEqualTo(members.get(0));
        assertThat(findMember2).isEqualTo(members.get(1));
    }

    @Test
    public void 멤버저장_예외_findById() throws Exception {
        //given
        Member member1 = createMember("ko");

        //when
        memberJpaRepository.save(member1);

        //then
        org.junit.jupiter.api.Assertions.assertThrows(
                NotMemberException.class, () -> {
                    memberJpaRepository.findById(45L).orElseThrow(() -> {throw new NotMemberException();});
                }
        );

        Throwable exception = org.junit.jupiter.api.Assertions.assertThrows(
                NotMemberException.class, () -> {
                    memberJpaRepository.findById(45L).orElseThrow(() -> {throw new NotMemberException();});
                }
        );
        org.junit.jupiter.api.Assertions.assertEquals("member not found", exception.getMessage());

    }

    @Test
    public void 리스트_조회검증() throws Exception {
        //given
        List<Member> members = memberCreateAndSave();

        //when
        List<Member> allMembers = memberJpaRepository.findAll();
        long memberCount = memberJpaRepository.count();

        //then
        assertThat(allMembers.size()).isEqualTo(2);
        assertThat(memberCount).isEqualTo(2);

    }

    @Test
    public void 삭제_검증() throws Exception {
        //given
        List<Member> members = memberCreateAndSave();

        //when
        memberJpaRepository.delete(members.get(0));
        long count = memberJpaRepository.count();

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

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        allMembers.add(member1);
        allMembers.add(member2);

        return allMembers;
    }

}