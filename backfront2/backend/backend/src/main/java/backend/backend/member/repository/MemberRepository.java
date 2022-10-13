package backend.backend.member.repository;

import backend.backend.member.domain.Member;
import backend.backend.member.enumerate.MemberProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialId(String socialId);

    Optional<Member> findMemberByEmailAndMemberProvider(String email, MemberProvider memberProvider);

}
