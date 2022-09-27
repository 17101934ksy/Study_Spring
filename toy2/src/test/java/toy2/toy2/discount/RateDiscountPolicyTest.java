package toy2.toy2.discount;

import org.junit.jupiter.api.Test;
import toy2.toy2.service.discount.DiscountPolicy;
import toy2.toy2.service.discount.RateDiscountPolicy;
import toy2.toy2.domain.Grade;
import toy2.toy2.domain.Member;

import static org.assertj.core.api.Assertions.assertThat;

class RateDiscountPolicyTest {

    @Test
    void calculateRateDiscount(){

        Member member1 = new Member(1L, "ttt", Grade.VIP);
        Member member2 = new Member(1L, "ttt", Grade.BASIC);

        DiscountPolicy rateDiscount = new RateDiscountPolicy();

        assertThat(rateDiscount.discount(member1, 10000)).isEqualTo(1000);
        assertThat(rateDiscount.discount(member1, 30000)).isEqualTo(3000);

        assertThat(rateDiscount.discount(member2, 10000)).isEqualTo(0);
        assertThat(rateDiscount.discount(member2, 30000)).isEqualTo(0);

    }
}