package kakaoToy1.kakaoToy1.repository;

import kakaoToy1.kakaoToy1.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyMemberRepository extends JpaRepository<Member, Long> {
}
