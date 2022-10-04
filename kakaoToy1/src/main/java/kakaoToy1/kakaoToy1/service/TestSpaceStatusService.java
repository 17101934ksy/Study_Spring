package kakaoToy1.kakaoToy1.service;

import kakaoToy1.kakaoToy1.domain.TestSpace;
import kakaoToy1.kakaoToy1.domain.member.Member;
import kakaoToy1.kakaoToy1.repository.MemberRepository;
import kakaoToy1.kakaoToy1.repository.TestSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static kakaoToy1.kakaoToy1.domain.TestSpaceStatus.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TestSpaceStatusService {

    private final MemberRepository memberRepository;
    private final TestSpaceRepository testSpaceRepository;


    public void setGuestSessionStatus(HttpSession session, String testSpaceId) throws  Exception {
        validateSession(session);

        if (session.getAttribute(testSpaceId + "_status") == null) {
            session.setAttribute(testSpaceId + "_status", GUEST);
        }
    }


    public void changeStaffSessionStatus(HttpSession session, String testSpaceId) throws Exception {

        validateSession(session);

        if (session.getAttribute(testSpaceId + "_status") == GUEST){
            session.setAttribute(testSpaceId + "_status", STAFF);
        } else if(session.getAttribute(testSpaceId + "_status") == STAFF){
            session.setAttribute(testSpaceId + "_status", GUEST);
        }
    }

    /**
     * 시험장의 master일 경우, 마스터로 직위 등록하는 메소드
     */
    public void changeMasterSessionStatus(HttpSession session, String testSpaceId) throws Exception {
        Optional<Member> findMember = validateSession(session);

        if (findMember.isPresent()) {
            Optional<List<TestSpace>> findTestSpace = testSpaceRepository.findByMember(findMember.get());

            for (TestSpace testSpace : findTestSpace.get()) {
                if (String.valueOf(testSpace.getId()).equals(testSpaceId)){
                    session.setAttribute(testSpaceId + "_status", MASTER);
                    break;
                }
            }
        }
    }

    /**
     * 세션 검증
     */
    private Optional<Member> validateSession(HttpSession session) throws Exception {
        Optional<Member> findMember = memberRepository.findByLoginId((String) session.getAttribute("sessionId"));

        if (findMember.isEmpty()) {
            session.invalidate();
            throw new Exception("등록된 사용자가 아닙니다.");

        }
        return findMember;
    }
}
