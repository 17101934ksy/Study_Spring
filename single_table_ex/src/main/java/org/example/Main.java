package org.example;

import org.example.singletable.domain.item.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello2");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
            Member member = new Member();
            member.setName("ko");
            em.persist(member);

            Delivery delivery = new Delivery();
            delivery.setCity("seoul");
            em.persist(delivery);


            Orders order = new Orders();
            order.setMember(member);
            order.setDelivery(delivery);
            em.persist(order);

            Movie movie = new Movie();
            movie.setActor("kim");
            movie.setDirector("ko");
            movie.setPrice(1000);
            movie.setStockQuantity(10);

            em.persist(movie);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}