package org.example;

import org.example.domain.Member;
import org.example.domain.DTO.MemberDTO;
import org.example.domain.Team;
import org.example.domain.embed.Address;

import javax.persistence.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello5");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
            Team team1 = new Team();
            team1.setName("ttt");

            Member member1 = new Member();
            member1.setName("hello");
            member1.setAge(12);
            member1.setTeam(team1);

            Member member2 = new Member();
            member2.setName("hello2");
            member2.setTeam(team1);

            em.persist(member1);
            em.persist(member2);
            em.persist(team1);
            em.flush();

            List<Member> findMembers = em.createQuery("select m from Member m", Member.class).getResultList();
            for (Member findMember : findMembers) {
                System.out.println("m.id = " + findMember.getId());
            }
            Member findResult = em.createQuery("select m from Member m where m.name =: username", Member.class)
                    .setParameter("username", member1.getName())
                    .getSingleResult();

            Member findResult2 = em.createQuery("select m from Member m where m.name =: username", Member.class)
                            .setParameter("username", member1.getName())
                                    .getSingleResult();

            List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class).getResultList();

            List<Address> resultList = em.createQuery("select o.homeAddress from Orders o", Address.class)
                    .getResultList();

            List resultList2 = em.createQuery("select m.name, m.age from Member m")
                            .getResultList();

            for (Object o : resultList2) {
                Object[] r = (Object[]) o;
                System.out.println("result = " + r[0]);
                System.out.println("result = " + r[1]);
            }

            System.out.println("==================");
            for (Address address : resultList) {
                System.out.println(address.fullAddress());
            }
            System.out.println("==================");

            System.out.println(findResult.getName());


            List<Object[]> result3 = em.createQuery("select m.name, m.age from Member m")
                            .getResultList();
            for (Object[] objects : result3) {
                System.out.println("result = " + objects[0]);
                System.out.println("result = " + objects[1]);

            }

            List<MemberDTO> resultList1 = em.createQuery("select new org.example.domain.DTO.MemberDTO(m.name, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            System.out.println("==============DTO=============");
            for (MemberDTO memberDTO : resultList1) {
                System.out.println("result = " + memberDTO.getName());
                System.out.println("result = " + memberDTO.getAge());
            }
            System.out.println("==============DTO=============");



            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}