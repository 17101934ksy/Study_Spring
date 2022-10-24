package study.datajpa.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.repository.MemberRepository;

import java.util.Optional;

@SpringBootTest
@Transactional
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 팀_처음_설정하기() throws Exception {
        //given

        Member member = createMember("ko");
        Team team = createTeam("kia");

        //when
        member.updateTeam(team);

        Member findMember = saveAndFindMember(member);

        //then
        Assertions.assertThat(findMember.getTeam().getName()).isEqualTo("kia");

    }

    @Test
    public void 팀_수정하기() throws Exception {
        //given
        Member memberA = createMember("ko");
        Member memberB = createMember("ko");

        Team teamA = createTeam("A");
        Team teamB = createTeam("B");

        memberA.updateTeam(teamA);
        memberB.updateTeam(teamA);

        //when
        memberA.updateTeam(teamB);
        memberB.updateTeam(teamB);

        //then
        Assertions.assertThat(teamA.getMembers().size()).isEqualTo(0);
        Assertions.assertThat(teamB.getMembers().size()).isEqualTo(2);

    }

    private Member saveAndFindMember(Member member) {
        Member saveMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(saveMember.getId()).get();
        return findMember;
    }

    private static Team createTeam(String name) {
        return Team
                .builder()
                .name(name)
                .build();
    }

    private static Member createMember(String name) {
        return Member
                .builder()
                .username(name)
                .age(26)
                .build();
    }


    @Test
    public void OptionalTest() throws Exception {
        //given
        Optional<String> optionalS = Optional.empty();

        //when

        //then
        System.out.println("===================================================");
        System.out.println("optionalS.get() = " + optionalS.isEmpty());
        System.out.println("optionalS.toString() = " + optionalS.toString());
        System.out.println("===================================================");
        
        
    }
}