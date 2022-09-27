package toy2.toy2.service.discount;

import toy2.toy2.domain.Grade;
import toy2.toy2.domain.Member;

public class FixDiscountPolicy implements DiscountPolicy{

    private static int discountPrice = 1000;

    @Override
    public int discount(Member member, int price) {

        if (member.getGrade() == Grade.VIP){
            return discountPrice;
        } else {
            return 0;
        }
    }
}
