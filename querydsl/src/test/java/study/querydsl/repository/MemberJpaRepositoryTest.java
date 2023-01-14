package study.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.domain.Member;
import study.querydsl.domain.Team;
import study.querydsl.dto.MemberSearchCond;
import study.querydsl.dto.MemberTeamDto;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void basicTest1() throws Exception {
        //given
        Member member = new Member("member1", 10);
        memberJpaRepository.save(member);

        //when
        Member findMember = memberJpaRepository.findById(member.getId()).get();

        //then
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void basicTest2() throws Exception {
        //given
        Member member = new Member("member1", 10);
        memberJpaRepository.save(member);

        //when
        List<Member> result = memberJpaRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void basicQueryDslTest1() throws Exception {
        //given
        Member member = new Member("member1", 10);
        memberJpaRepository.save(member);

        //when
        List<Member> result = memberJpaRepository.findAllQueryDsl();

        //then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void basicQueryDslTest2() throws Exception {
        //given
        Member member = new Member("member1", 10);
        memberJpaRepository.save(member);

        //when
        List<Member> result = memberJpaRepository.findByUsernameQueryDsl("member1");

        //then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void searchTest() throws Exception {
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        //when
        MemberSearchCond condition = new MemberSearchCond();
        condition.setAgeGoe(40);
        condition.setAgeLoe(50);
        condition.setTeamName("teamB");

        List<MemberTeamDto> result = memberJpaRepository.searchByBuilder(condition);

        //then
        assertThat(result.get(0).getUsername()).isEqualTo("member4");

    }

    @Test
    public void searchTest2() throws Exception {
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        //when
        MemberSearchCond condition = new MemberSearchCond();
        condition.setAgeGoe(40);
        condition.setAgeLoe(50);
        condition.setTeamName("teamB");

        List<MemberTeamDto> result = memberJpaRepository.search(condition);

        //then
        assertThat(result.get(0).getUsername()).isEqualTo("member4");

    }

}