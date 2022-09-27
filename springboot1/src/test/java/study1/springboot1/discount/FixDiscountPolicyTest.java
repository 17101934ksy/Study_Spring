package study1.springboot1.discount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import study1.springboot1.member.Grade;
import study1.springboot1.member.Member;

import static org.assertj.core.api.Assertions.assertThat;

class FixDiscountPolicyTest {

    @Test
    void calculateFixDiscount(){
        DiscountPolicy fixDiscountPolicy = new FixDiscountPolicy();

        Member member1 = new Member(1L, "kkk", Grade.VIP);
        Member member2 = new Member(2L, "kkk", Grade.BASIC);

        assertThat(fixDiscountPolicy.discount(member1, 10000)).isEqualTo(1000);
        assertThat(fixDiscountPolicy.discount(member2, 10000)).isEqualTo(0);

    }
}