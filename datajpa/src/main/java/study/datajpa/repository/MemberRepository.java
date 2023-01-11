package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.datajpa.domain.Member;
import study.datajpa.dto.MemberDto;

import java.util.Collection;
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

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    Optional<List<MemberDto>> findMemberDto();

    @Query("select m from Member m where m.username in :names")
   Optional<List<Member>> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUsername(String username); //컬렉션
    Member findMemberByUsername(String username); // 단건
    Optional<Member> findOptionalByUsername(String username);// optional

    @Query(value = "select m from Member m",
            countQuery = "select count(m.username) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

//    Slice<Member> findByAge(int age, Pageable pageable);


    Page<Member> findByUsername(String username, Pageable pageable);

//    @Query(value = "select m.id, s.id from Member m join School s where m.username = :username",
//            countQuery = "select count (m.username) from Member m")
//    Slice<Member> findMemberByUsername(String username, Pageable pageable);


}
