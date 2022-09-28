package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    @Test
    void createOrder(){

        Member member = new Member(1L, "ttt", Grade.VIP);

        MemberRepository memberRepository = new MemoryMemberRepository();

        memberRepository.save(member);

        DiscountPolicy discountPolicy = new RateDiscountPolicy();

        OrderService orderService = new OrderServiceImpl(memberRepository, discountPolicy);
        Orders order = orderService.createOrder(1L, "Ttt", 10000);

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);

    }
}