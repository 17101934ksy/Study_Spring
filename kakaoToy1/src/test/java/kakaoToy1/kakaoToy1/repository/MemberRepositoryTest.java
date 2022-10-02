package kakaoToy1.kakaoToy1.repository;

import kakaoToy1.kakaoToy1.domain.member.Member;
import kakaoToy1.kakaoToy1.domain.member.MemberAuthStatus;
import kakaoToy1.kakaoToy1.domain.member.MemberLoginWay;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class MemberRepositoryTest {

    @Autowired private MemberRepository memberRepository;

    @Test
    public void 유저정보저장() throws Exception {

        //given
        Member member = new Member(MemberLoginWay.KAKAO, "1000");
        Member duplicatedMember = new Member(MemberLoginWay.KAKAO, "2000");
        Member member2 = new Member(MemberLoginWay.KAKAO, "2000");

        //when
        final Member saveMember = memberRepository.save(member);
        final Member saveDuplicatedMember = memberRepository.save(duplicatedMember);

        final Member saveMember2 = memberRepository.save(member2);

        //then
        assertThat(saveMember).isSameAs(member);
        assertThat(saveDuplicatedMember.getId()).isNotEqualTo(member.getId());
        assertThat(saveDuplicatedMember.getLoginId()).isEqualTo("2000");

    }


    @Test
    public void 유저정보수정() throws Exception {
        //given
        Member member = new Member(MemberLoginWay.KAKAO, "1000");

        //when
        final Member saveMember = memberRepository.save(member);

        Optional<Member> findMember = memberRepository.findByLoginId(saveMember.getLoginId());
        findMember.get().changeMemberData("koseyun", "학생", 17101934L,
                "서울과기대", "산공", "ksyn1611@naver.com");

        Optional<Member> changeMember = memberRepository.findByLoginId(saveMember.getLoginId());

        //then
        assertThat(changeMember.toString()).isEqualTo(findMember.toString());
        assertThat(findMember.get().getDepartment()).isEqualTo("산공");
        assertThat(saveMember.getUniversity()).isEqualTo("서울과기대");
    }

    @Test
    public void 유저권한수정() throws Exception {
        //given
        Member member = new Member(MemberLoginWay.KAKAO, "1000");

        //when
        final Member saveMember = memberRepository.save(member);

        Optional<Member> findMember = memberRepository.findByLoginId(saveMember.getLoginId());

        findMember.get().changeMemberData("koseyun", "학생", 17101934L,
                "서울과기대", "산공", "ksyn1611@naver.com");

        findMember.get().changeMemberStatus(MemberAuthStatus.STUDENT);

        Optional<Member> changeStatusMember = memberRepository.findByLoginId(saveMember.getLoginId());

        //then

        assertThat(changeStatusMember.get().getStatus()).isEqualTo(MemberAuthStatus.STUDENT);
        assertThat(changeStatusMember.get().getId()).isEqualTo(findMember.get().getId());
        assertThat(changeStatusMember.get().getId()).isEqualTo(saveMember.getId());
        assertThat(findMember.isPresent()).isTrue();
    }

    @Test
    public void 유저찾기ById() throws Exception {

        boolean memberIsEmpty;
        boolean memberIsPresent;
        //given
        Optional<Member> member = memberRepository.findById("10000L");

        //when
        memberIsEmpty = member.isEmpty();
        memberIsPresent = member.isPresent();

        //then
        assertThat(memberIsEmpty).isTrue();
        assertThat(memberIsPresent).isFalse();
    }

    @Test
    public void 유저찾기ByLoginId() throws Exception {
        //given
        Member member = new Member(MemberLoginWay.KAKAO, "1000");

        //when
        Member saveMember = memberRepository.save(member);

        Optional<Member> memberByMemberUserId = memberRepository.findByLoginId(member.getLoginId());

        //then

        assertThat(memberByMemberUserId.get().getLoginId()).isEqualTo(member.getLoginId());
    }
}