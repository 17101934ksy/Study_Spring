package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.items.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit() {
            Member memberA = createMember("ko");
            Member memberB = createMember("ko2");

            em.persist(memberA);
            em.persist(memberB);

            Book book1 = createBook("jpaA", 10000, 100, "123");
            Book book2 = createBook("jpaB", 9000, 120, "126");

            em.persist(book1);
            em.persist(book2);


            Book book3 = createBook("jpaC", 9000, 120, "126");
            Book book4 = createBook("jpaD", 9000, 120, "126");

            em.persist(book3);
            em.persist(book4);

            OrderItem orderItem1 = createOrderItem(book1, 40);
            OrderItem orderItem2 = createOrderItem(book2, 37);

            Delivery deliveryA = createDelivery(memberA);
            Order orderA = Order.createOrder(memberA, deliveryA, orderItem1, orderItem2);

            em.persist(orderA);


            OrderItem orderItem3 = createOrderItem(book1, 40);
            OrderItem orderItem4 = createOrderItem(book2, 37);

            Delivery deliveryB = createDelivery(memberB);
            Order orderB = Order.createOrder(memberB, deliveryB, orderItem3, orderItem4);

            em.persist(orderB);
        }

        private static Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private static OrderItem createOrderItem(Book book1, int count) {
            return OrderItem.createOrderItem(book1, book1.getPrice(), count);
        }

        private static Book createBook(String jpaA, int price, int stockQuantity, String isbn) {
            return Book.builder()
                    .name(jpaA)
                    .price(price)
                    .author("ko1")
                    .stockQuantity(stockQuantity)
                    .isbn(isbn)
                    .build();
        }

        private static Member createMember(String name) {
            Member member = new Member(name, new Address("seoul", "han-river", "111-111"));
            return member;
        }
    }


}
