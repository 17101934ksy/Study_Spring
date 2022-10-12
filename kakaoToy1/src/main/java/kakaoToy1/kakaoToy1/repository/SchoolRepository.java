package kakaoToy1.kakaoToy1.repository;

import kakaoToy1.kakaoToy1.domain.member.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

}
