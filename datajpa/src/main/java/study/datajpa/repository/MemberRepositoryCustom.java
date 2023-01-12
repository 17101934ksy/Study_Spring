package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.domain.Member;

import java.util.List;


public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
