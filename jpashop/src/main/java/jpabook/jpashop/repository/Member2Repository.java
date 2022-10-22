package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class Member2Repository {

    private final EntityManager em;


    public Long save(Member2 member2) {
        em.persist(member2);
        return member2.getId();
    }

    public Member2 findOne(Long id) {
        return em.find(Member2.class, id);
    }

}