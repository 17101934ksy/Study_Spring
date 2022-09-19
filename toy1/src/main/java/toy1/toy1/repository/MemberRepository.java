package toy1.toy1.repository;

import org.springframework.stereotype.Repository;
import toy1.toy1.domain.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
