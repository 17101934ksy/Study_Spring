package toy2.toy2.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import toy2.toy2.domain.Orders;
import toy2.toy2.service.discount.DiscountPolicy;
import toy2.toy2.service.discount.RateDiscountPolicy;
import toy2.toy2.domain.Grade;
import toy2.toy2.domain.Member;
import toy2.toy2.repository.MemberRepository;
import toy2.toy2.repository.MemoryMemberRepository;
import toy2.toy2.service.order.OrderService;
import toy2.toy2.service.order.OrderServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceImplTest {

    @Test
    void createOrders(){

        MemberRepository memberRepository = new MemoryMemberRepository();
        DiscountPolicy discountPolicy = new RateDiscountPolicy();

        OrderService orderService = new OrderServiceImpl(memberRepository, discountPolicy);

        Member member1 = new Member(1L, "ttt", Grade.VIP);
        Orders order1 = orderService.createOrder(1L, member1.getId(), "ttt", 10000, discountPolicy.discount(member1, 10000));

        Member member2 = new Member(1L, "ttt", Grade.VIP);
        Orders order2 = orderService.createOrder(2L, member2.getId(), "ttt", 30000, discountPolicy.discount(member2, 30000));

        assertThat(order1.getDiscountPrice()).isEqualTo(1000);
        assertThat(order2.getDiscountPrice()).isEqualTo(3000);

        order1.calculateDiscount();
        order2.calculateDiscount();

        assertThat(order1.getPrice()).isEqualTo(9000);
        assertThat(order2.getPrice()).isEqualTo(27000);


        Member member3 = new Member(1L, "ttt", Grade.BASIC);
        Orders order3 = orderService.createOrder(3L, member3.getId(), "ttt", 10000, discountPolicy.discount(member3, 10000));

        Member member4 = new Member(1L, "ttt", Grade.BASIC);
        Orders order4 = orderService.createOrder(4L, member4.getId(), "ttt", 30000, discountPolicy.discount(member4, 30000));

        assertThat(order3.getDiscountPrice()).isEqualTo(0);
        assertThat(order4.getDiscountPrice()).isEqualTo(0);


    }
}