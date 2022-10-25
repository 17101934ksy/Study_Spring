package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.datajpa.domain.Member;
import study.datajpa.memberdto.MemberDto;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Override
    Optional<Member> findById(Long id);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findMembers(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    Optional<List<String>> findUsernameList();

    @Query("select new study.datajpa.memberdto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    Optional<List<MemberDto>> findMemberDto();

    @Query("select m from Member m where m.username in :names")
   Optional<List<Member>> findByNames(@Param("names") Collection<String> names);

}
