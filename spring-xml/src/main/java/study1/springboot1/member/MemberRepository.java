package study1.springboot1.member;

public interface MemberRepository {

    void save(Member member);

    void removeMember(Long memberId);

    Member findById(Long memberId);

}
