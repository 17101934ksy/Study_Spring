package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;


    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }


    //안쓰므로 복붙!!!
    public List<Order> findAllByString(OrderSearch orderSearch) {
        //language=JPAQL
        String jpql = "select o From Order o join o.member m";
        boolean isFirstCondition = true;

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }
        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000); //최대 1000건
        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }


    // fetch 조인
    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class)
                .getResultList();
    }

    public List<Order> findAllWithItems() {
        return em.createQuery(
                        "select o from Order o" +
                                " join fetch o.member m" +
                                " join fetch o.delivery d" +
                                " join fetch o.orderItems r" +
                                " join fetch r.item i", Order.class)
                .getResultList();

        //.setMaxResults(100) <- 페이징을 메모리에 올려놓은 후에 처리를 진행함 -> 메모리 오버플로우 발생 !!!
    }

    public List<Order> findAllWithDistinctItems() {
        return em.createQuery(
                        "select distinct o from Order o" +
                                " join fetch o.member m" +
                                " join fetch o.delivery d" +
                                " join fetch o.orderItems r" +
                                " join fetch r.item i", Order.class)
                .getResultList();

        //.setMaxResults(100) <- 페이징을 메모리에 올려놓은 후에 처리를 진행함 -> 메모리 오버플로우 발생 !!!
    }

}
