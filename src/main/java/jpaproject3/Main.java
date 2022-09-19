package jpaproject3;

import jpaproject3.jpabook.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.Order;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        System.out.println("Hello world!");

        tx.begin();

        try{
            Member member1 = new Member("koseyun", "kkk", "lll");
            em.persist(member1);

            Member member2 = new Member("kose11yun", "kkk", "lll");
            em.persist(member2);

            Item item1 = new Item("banana", 1700, 10);
            Item item2 = new Item("apple", 4000, 10);
            Item item3 = new Item("watermelon", 6000, 10);
            Item item4 = new Item("melon", 3400, 10);

            em.persist(item1);
            em.persist(item2);
            em.persist(item3);
            em.persist(item4);

            em.flush();
            em.clear();

            Member member = em.find(Member.class, member2.getId());

            Orders order1 = new Orders(member, LocalDateTime.now(), OrderStatus.ORDER);

            OrderItem orderItem1 = new OrderItem(order1, item1, item1.getPrice() * item1.getStuckQuantity(), 5);
            em.persist(orderItem1);

            order1.addOrderItemList(orderItem1);

            em.persist(order1);
            em.flush();
            em.clear();

            Orders findOrder = em.find(Orders.class, order1.getOrderId());

            System.out.println("===========================");
            System.out.println(member.getName());
            System.out.println(item1.getPrice() * item1.getStuckQuantity());
            System.out.println((findOrder.getOrderItemList()).get(0).getItem().getName());
            System.out.println("===========================");


            tx.commit();

        }catch(Exception e){

            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}