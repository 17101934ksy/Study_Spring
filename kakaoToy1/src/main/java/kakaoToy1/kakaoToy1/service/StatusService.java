package kakaoToy1.kakaoToy1.service;

import kakaoToy1.kakaoToy1.domain.TestSpaceStatus;
import kakaoToy1.kakaoToy1.domain.member.Member;
import kakaoToy1.kakaoToy1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusService {


    private final MemberRepository memberRepository;

    public void changeSessionStatus(HttpSession session) throws Exception {

        Optional<Member> findMember = memberRepository.findByLoginId((String) session.getAttribute("sessionId"));

        if (findMember.isEmpty()) {
            session.invalidate();
            System.out.println("findMember is not exists");
            throw new Exception("세션이 존재하지 않습니다.");
        }

        switch (findMember.get().getStatus()){
            case ADMIN:
                session.setAttribute("status", TestSpaceStatus.ADMIN);
                break;

            case GUEST:

            case STUDENT:
                session.setAttribute("status", TestSpaceStatus.GUEST);
                break;

            case PROFESSOR:
                session.setAttribute("status", TestSpaceStatus.MASTER);
                break;

            case STAFF:
                session.setAttribute("status", TestSpaceStatus.STAFF);
                break;
        }
    }
}
