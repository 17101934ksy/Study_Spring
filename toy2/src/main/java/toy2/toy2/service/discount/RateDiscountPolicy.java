package toy2.toy2.service.discount;

import org.springframework.stereotype.Component;
import toy2.toy2.domain.Grade;
import toy2.toy2.domain.Member;

@Component
public class RateDiscountPolicy implements DiscountPolicy{

    private static int discountRate = 10;

    @Override
    public int discount(Member member, int price) {

        if(member.getGrade() == Grade.VIP){
            return price * discountRate / 100;
        } else {
            return 0;
        }

    }
}
