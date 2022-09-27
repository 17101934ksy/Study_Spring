package study1.springboot1.discount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import study1.springboot1.member.Grade;
import study1.springboot1.member.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {

    @Test
    void calculateRateDiscount(){

        DiscountPolicy rateDiscountPolicy = new RateDiscountPolicy();

        Member member1 = new Member(1L, "kkk", Grade.VIP);
        Member member2 = new Member(2L, "kkk", Grade.BASIC);

        assertThat(rateDiscountPolicy.discount(member1, 10000)).isEqualTo(1000);
        assertThat(rateDiscountPolicy.discount(member1, 20000)).isEqualTo(2000);
        assertThat(rateDiscountPolicy.discount(member2, 20000)).isEqualTo(0);
    }

}