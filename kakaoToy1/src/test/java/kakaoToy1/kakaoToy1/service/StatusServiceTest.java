package kakaoToy1.kakaoToy1.service;

import kakaoToy1.kakaoToy1.domain.TestSpaceStatus;
import kakaoToy1.kakaoToy1.domain.member.Member;
import kakaoToy1.kakaoToy1.domain.member.MemberAuthStatus;
import kakaoToy1.kakaoToy1.domain.member.MemberLoginWay;
import kakaoToy1.kakaoToy1.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Primary;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static kakaoToy1.kakaoToy1.domain.member.MemberAuthStatus.*;
import static kakaoToy1.kakaoToy1.domain.member.MemberLoginWay.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class StatusServiceTest {


    @Autowired
    MemberRepository memberRepository;

    MockHttpSession session;
    MockHttpServletRequest request;

    StatusService statusService;

    @Test
    public void 세션_테스트() throws Exception {
        //given
        session = new MockHttpSession();

        //when
        session.setAttribute("name", "test");

        //then
        assertThat(session.getAttribute("name")).isEqualTo("test");
    }

    @Test
    public void 세션_직위부여_테스트() throws Exception {

        statusService = new StatusService(memberRepository);

        //given
        session = new MockHttpSession();
        Member member = new Member(KAKAO, "2000100");

        member.changeMemberStatus(ADMIN);
        memberRepository.save(member);

        //when

        session.setAttribute("sessionId", "2000100");

        statusService.changeSessionStatus(session);

        //then
        assertThat(session.getAttribute("status")).isEqualTo(TestSpaceStatus.ADMIN);
    }

}