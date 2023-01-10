package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.items.Book;
import jpabook.jpashop.domain.items.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember("membe1", "서울", "강가", "123-123");

        Item book = createBook("jap", 100, 10);

        //when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 100 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 제고가 줄어야 한다.", 8, book.getStockQuantity());

    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember("membe1", "서울", "강가", "123-123");
        Item item = createBook("시골 jpa", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order order = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 Cancel이 된다.", OrderStatus.CANCEL, order.getStatus());
        assertEquals("주문 추소한 상품은 그만큼 재고가 증가해야 한다.", 10, item.getStockQuantity());

    }
    
    @Test
    public void 상품재고수량초과() throws Exception {
        //given
        Member member = createMember("member2", "부산", "해운대", "100-100");
        Item book = createBook("jpa2", 100, 10);

        //when
        int orderCount = 11;

        //then
        Assertions.assertThrows(NotEnoughStockException.class, () ->
        { orderService.order(member.getId(), book.getId(), orderCount); });
    }

    //== 테스트에만 사용되는 공통 인스턴스 생성 메소드 ==//
    private Item createBook(String name, int price, int quantity) {
        Item book = new Book(name, price, quantity);
        em.persist(book);
        return book;
    }

    private Member createMember(String name, String city, String street, String zipcode) {
        Member member =  new Member();
        member.updateName(name);
        member.updateAddress(new Address(city, street, zipcode));
        em.persist(member);
        return member;
    }

}