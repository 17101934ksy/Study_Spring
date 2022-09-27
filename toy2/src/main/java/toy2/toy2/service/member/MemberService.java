package toy2.toy2.service.member;

import toy2.toy2.domain.Member;

public interface MemberService {

    void join(Member member);

    void remove(Member member);

    Member findMember(Long memberId);

    int sizeMember();



}
