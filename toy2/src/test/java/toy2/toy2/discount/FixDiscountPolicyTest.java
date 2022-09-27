package toy2.toy2.discount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import toy2.toy2.service.discount.DiscountPolicy;
import toy2.toy2.service.discount.FixDiscountPolicy;
import toy2.toy2.domain.Grade;
import toy2.toy2.domain.Member;

class FixDiscountPolicyTest {

    @Test
    void calculateFixDiscount(){

        Member member1 = new Member(1L, "kkk",Grade.VIP);
        Member member2 = new Member(2L, "kkk",Grade.BASIC);

        DiscountPolicy discountPolicy = new FixDiscountPolicy();

        Assertions.assertThat(discountPolicy.discount(member1, 2000)).isEqualTo(1000);
        Assertions.assertThat(discountPolicy.discount(member2, 20000)).isEqualTo(0);
    }
}