package org.example;

import org.example.domain.Member;
import org.example.domain.Orders;
import org.example.domain.Team;
import org.example.domain.enums.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello3");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{

            Team team = new Team();
            team.setName("hohoho");
            em.persist(team);

            Member member = new Member();
            member.setName("kim");
            member.setTeam(team);

            Orders order = new Orders();
            order.setStatus(OrderStatus.ORDER);

            em.persist(member);
            em.persist(order);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());
            System.out.println(findMember.getTeam().getClass());

            System.out.println("==========================");
            findMember.getTeam().getClass();
            System.out.println("==========================");


            tx.commit();

        } catch(Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }
}