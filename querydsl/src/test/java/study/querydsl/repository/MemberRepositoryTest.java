package study.querydsl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.domain.Member;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void basicTest1() throws Exception {
        //given
        Member member = new Member("member1", 10);
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findById(member.getId()).get();

        //then
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

}