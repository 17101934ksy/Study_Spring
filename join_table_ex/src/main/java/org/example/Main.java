package org.example;

import org.example.domain.*;
import org.example.domain.enums.OrderStatus;
import org.example.domain.imbed.Address;
import org.example.domain.imbed.Period;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello3");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{

            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);

            Team team = new Team();
            team.setName("hohoho");
            em.persist(team);

            Member member = new Member();
            member.setName("kim");
            member.setTeam(team);

            member.getFavoriteFoods().add("chicken");
            member.getFavoriteFoods().add("chicken");
            member.getFavoriteFoods().add("pizza");

//            member.getAddressHistory().add(new Address("city1", "seoul", "2003"));

            em.persist(team);
            em.persist(member);

            Period period1 = new Period(LocalDateTime.now(), LocalDateTime.now());
            Address addr1 = new Address("seoul", "seoul", "10000");
            Address addr2 = new Address("seoul", "seoul", "10000");



            System.out.println(addr1.getStreet());
            System.out.println(addr2.getStreet());
            System.out.println("addr1 == addr2: " + addr1.equals(addr2));

            Member findMember = em.find(Member.class, member.getId());

            findMember.setHomeAddress((new Address("ttt", "Tttt", "1000")));

            System.out.println("===========================");
            findMember.getFavoriteFoods().remove("chicken");
            findMember.getFavoriteFoods().add("hansick");
            System.out.println("===========================");

            Delivery delivery = new Delivery();
            delivery.setStatus(OrderStatus.ORDER);

            Orders order1 = new Orders();
            OrderItem orderItem = new OrderItem();
            Item item = new Item();

            orderItem.setItem(item);
            orderItem.setOrder(order1);
            delivery.setOrder(order1);

            delivery.getAddressEntityList().add(new AddressEntity("ww", "ee", "11"));
            em.persist(item);
            em.persist(orderItem);
            em.persist(order1);
            em.persist(delivery);

            Member member3 = new Member();
            member3.setName("ewefhello");
            em.persist(member3);

            Member member4 = new Member();
            member4.setName("hello");
            em.persist(member4);

            Member member5 = new Member();
            member5.setName("hello123");
            em.persist(member5);

            Member member6 = new Member();
            member6.setName("eehelloee");
            em.persist(member6);

            em.flush();
            em.clear();

            String jpql = "select m from Member m where m.name like '%hello%'";

            List<Member> findJpqlMember = em.createQuery(jpql, Member.class).
                    getResultList();


            System.out.println("=============================");
            for(Member m: findJpqlMember){
                System.out.println("m.id = " + m.getId());
            }
            System.out.println("=============================");
            tx.commit();

        } catch(Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }
}