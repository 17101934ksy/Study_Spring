package kakaoToy1.kakaoToy1.service;

import kakaoToy1.kakaoToy1.domain.TestSpace;
import kakaoToy1.kakaoToy1.domain.member.Member;
import kakaoToy1.kakaoToy1.repository.MemberRepository;
import kakaoToy1.kakaoToy1.repository.TestSpaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static kakaoToy1.kakaoToy1.domain.TestSpaceStatus.*;
import static kakaoToy1.kakaoToy1.domain.member.MemberLoginWay.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TestSpaceStatusServiceTest {


    @Autowired MemberRepository memberRepository;

    @Autowired TestSpaceRepository testSpaceRepository;

    MockHttpSession session;
    MockHttpServletRequest request;

    TestSpaceStatusService testSpaceStatusService;

    @Test
    public void 세션_테스트() throws Exception {
        //given
        session = new MockHttpSession();

        //when
        session.setAttribute("name", "test");

        //then
        assertThat(session.getAttribute("name")).isEqualTo("test");
        assertThat(session.getAttribute("noneAttribute")).isEqualTo(null);
    }

    @Test
    public void 세션_회원검증() throws Exception {
        //given
        testSpaceStatusService = new TestSpaceStatusService(memberRepository, testSpaceRepository);
        session = new MockHttpSession();

        String testSpaceId = String.valueOf(10000L);

        //when
        session.setAttribute("sessionId", "2000100");

        //then
        Exception thrown = assertThrows(Exception.class, () ->
                testSpaceStatusService.changeStaffSessionStatus(session, testSpaceId));
        assertEquals("등록된 사용자가 아닙니다.", thrown.getMessage());
//        assertThat(session.getAttribute(testSpaceId + "status")).isEqualTo(TestSpaceStatus.STAFF);
    }

    @Test
    public void 세션_게스트_직위부여() throws Exception {
        //given
        testSpaceStatusService = new TestSpaceStatusService(memberRepository, testSpaceRepository);
        session = new MockHttpSession();

        String testSpaceId = String.valueOf(10000L);

        Member member = new Member(KAKAO, "2000100");
        memberRepository.save(member);

        //when
        session.setAttribute("sessionId", "2000100");
        testSpaceStatusService.setGuestSessionStatus(session, testSpaceId);

        //then
        assertThat(session.getAttribute(testSpaceId + "_status")).isEqualTo(GUEST);
    }

    @Test
    public void 세션_게스트_직위부여_예외() throws Exception {
        //given
        testSpaceStatusService = new TestSpaceStatusService(memberRepository, testSpaceRepository);
        session = new MockHttpSession();

        String testSpaceId = String.valueOf(10000L);

        Member member = new Member(KAKAO, "200010");
        memberRepository.save(member);

        //when
        session.setAttribute("sessionId", "2000100"); // 멤버 로그인 아이디와 다른 세션

        //then
        Exception thrown = assertThrows(Exception.class, () ->
                testSpaceStatusService.setGuestSessionStatus(session, testSpaceId));
        assertEquals("등록된 사용자가 아닙니다.", thrown.getMessage());
    }

    @Test
    public void 세션_스태프_직위부여() throws Exception {
        //given
        testSpaceStatusService = new TestSpaceStatusService(memberRepository, testSpaceRepository);
        session = new MockHttpSession();

        String testSpaceId = String.valueOf(10000L);
        Member member = new Member(KAKAO, "2000100");
        memberRepository.save(member);

        //when
        session.setAttribute("sessionId", "2000100");

        testSpaceStatusService.setGuestSessionStatus(session, testSpaceId);
        testSpaceStatusService.changeStaffSessionStatus(session, testSpaceId);

        //then
        assertThat(session.getAttribute(testSpaceId + "_status")).isEqualTo(STAFF);
    }

    @Test
    public void 세션_스태프에서_게스트_직위부여() throws Exception {
        //given
        testSpaceStatusService = new TestSpaceStatusService(memberRepository, testSpaceRepository);
        session = new MockHttpSession();

        String testSpaceId = String.valueOf(10000L);
        Member member = new Member(KAKAO, "2000100");
        memberRepository.save(member);

        //when
        session.setAttribute("sessionId", "2000100");

        testSpaceStatusService.setGuestSessionStatus(session, testSpaceId);
        testSpaceStatusService.changeStaffSessionStatus(session, testSpaceId);
        testSpaceStatusService.changeStaffSessionStatus(session, testSpaceId);

        //then
        assertThat(session.getAttribute(testSpaceId + "_status")).isEqualTo(GUEST);
    }

    @Test
    public void 세션_마스터_직위부여() throws Exception {
        //given
        testSpaceStatusService = new TestSpaceStatusService(memberRepository, testSpaceRepository);
        session = new MockHttpSession();

        String testSpaceId = String.valueOf(7L);
        Member member = new Member(KAKAO, "2000100");
        memberRepository.save(member);

        for(int i = 0; i < 20; i++) {
            TestSpace testSpace = new TestSpace("test_" + i, i+5, member);
            testSpaceRepository.save(testSpace);
        }

        //when
        session.setAttribute("sessionId", "2000100");

        testSpaceStatusService.changeMasterSessionStatus(session, testSpaceId);

        //then
        assertThat(session.getAttribute(testSpaceId + "_status")).isEqualTo(MASTER);
    }

    @Test
    public void 세션_마스터_직위부여X() throws Exception {
        //given
        testSpaceStatusService = new TestSpaceStatusService(memberRepository, testSpaceRepository);
        session = new MockHttpSession();

        String testSpaceId = String.valueOf(100L); // 해당 시험장의 주인이 아닐 때,
        Member member = new Member(KAKAO, "2000100");
        memberRepository.save(member);

        for(int i = 0; i < 20; i++) {
            TestSpace testSpace = new TestSpace("test_" + i, i+5, member);
            testSpaceRepository.save(testSpace);
        }

        //when
        session.setAttribute("sessionId", "2000100");

        testSpaceStatusService.setGuestSessionStatus(session, testSpaceId);
        testSpaceStatusService.changeMasterSessionStatus(session, testSpaceId);

        //then
        assertThat(session.getAttribute(testSpaceId + "_status")).isEqualTo(GUEST);
    }

    @Test
    public void 세션_마스터_방X() throws Exception {
        //given
        testSpaceStatusService = new TestSpaceStatusService(memberRepository, testSpaceRepository);
        session = new MockHttpSession();

        String testSpaceId = String.valueOf(100L); // 해당 시험장의 주인이 아닐 때,
        Member member = new Member(KAKAO, "2000100");
        memberRepository.save(member);

        //when
        session.setAttribute("sessionId", "2000100");

        testSpaceStatusService.setGuestSessionStatus(session, testSpaceId);
        testSpaceStatusService.changeMasterSessionStatus(session, testSpaceId);

        //then
        assertThat(session.getAttribute(testSpaceId + "_status")).isEqualTo(GUEST);
    }

}