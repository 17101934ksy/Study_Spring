package kakaoToy1.kakaoToy1.repository;

import kakaoToy1.kakaoToy1.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    // 소셜 로그인에 등록되는 인가 코드로 멤버 찾기
    Optional<Member> findByLoginId(String loginId);

    // 학사번으로 멤버 찾기
    Optional<Member> findByUniqueId(Long uniqueId);
}
