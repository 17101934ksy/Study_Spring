package kakaoToy1.kakaoToy1.repository;

import kakaoToy1.kakaoToy1.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;
    private EntityTransaction tx;

    @Transactional
    public Long save(Member member){
        tx.begin();
        try{
            em.persist(member);
            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        return member.getId();
    }

    public Member find(Long id){
        return em.find(Member.class, id);    }
}
