package kakaoToy1.kakaoToy1.repository;

import kakaoToy1.kakaoToy1.domain.TestSpace;
import kakaoToy1.kakaoToy1.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestSpaceRepository extends JpaRepository<TestSpace, String> {

    Optional<List<TestSpace>> findByMember(Member member);
}
