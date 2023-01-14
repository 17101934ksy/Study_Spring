package study.querydsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.querydsl.domain.Member;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    // select m from Member m where m.username = ?
    List<Member> findByUsername(String username);

}
