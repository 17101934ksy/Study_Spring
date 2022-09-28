package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em; //entity 매니저 등록

    //커맨드랑 쿼리를 분리해라. -> 저장을 하고 나면, 사이드이팩트를 일으킬 수 있으므로, 리턴값을 만들지 안되, 조회할 때 필요한 아이디만 리턴
    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
