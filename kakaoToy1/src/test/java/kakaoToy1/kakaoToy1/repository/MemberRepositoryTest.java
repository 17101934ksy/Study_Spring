package kakaoToy1.kakaoToy1.repository;

import kakaoToy1.kakaoToy1.domain.Member;
import kakaoToy1.kakaoToy1.domain.MemberAuthStatus;
import kakaoToy1.kakaoToy1.domain.MemberLoginWay;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class MemberRepositoryTest {

    @Autowired private MemberRepository memberRepository;

    @Test
    public void saveMember() throws Exception {

        //given
        Member member = new Member(MemberLoginWay.KAKAO, 1000L);

        //when
        final Member saveMember = memberRepository.save(member);

        //then
        assertThat(saveMember).isSameAs(member);
    }

    @Test
    public void changeMemberData() throws Exception {
        //given
        Member member = new Member(MemberLoginWay.KAKAO, 1000L);

        //when
        final Member saveMember = memberRepository.save(member);

        Optional<Member> findMember = memberRepository.findById(saveMember.getId());
        findMember.get().changeMemberData("koseyun", "학생", 17101934L,
                null, "서울과기대", "산공", "ksyn1611@naver.com");

        Optional<Member> changeMember = memberRepository.findById(saveMember.getId());

        //then
        assertThat(changeMember.toString()).isEqualTo(findMember.toString());
    }

    @Test
    public void changeMemberStatus() throws Exception {
        //given
        Member member = new Member(MemberLoginWay.KAKAO, 1000L);

        //when
        final Member saveMember = memberRepository.save(member);

        Optional<Member> findMember = memberRepository.findById(saveMember.getId());

        findMember.get().changeMemberData("koseyun", "학생", 17101934L,
                null, "서울과기대", "산공", "ksyn1611@naver.com");

        findMember.get().changeMemberStatus(MemberAuthStatus.STUDENT);

        Optional<Member> changeStatusMember = memberRepository.findById(saveMember.getId());

        //then

        assertThat(changeStatusMember.get().getStatus()).isEqualTo(MemberAuthStatus.STUDENT);
        assertThat(changeStatusMember.get().getId()).isEqualTo(findMember.get().getId());
        assertThat(changeStatusMember.get().getId()).isEqualTo(saveMember.getId());
    }
    
}