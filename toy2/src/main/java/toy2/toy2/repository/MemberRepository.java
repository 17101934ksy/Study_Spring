package toy2.toy2.repository;

import toy2.toy2.domain.Member;

public interface MemberRepository {
    void save(Member member);

    void remove(Member member);

    Member findById(Long memberId);

    int printSize();
}
