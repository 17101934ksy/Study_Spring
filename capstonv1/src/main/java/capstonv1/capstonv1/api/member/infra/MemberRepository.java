package capstonv1.capstonv1.api.member.infra;

import capstonv1.capstonv1.api.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findBySocialId(String socialId);
}
