package study1.springboot1.discount;

import study1.springboot1.member.Grade;
import study1.springboot1.member.Member;

public class FixDiscountPolicy implements DiscountPolicy{

    private int discountPrice = 1000;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP){
            return discountPrice;
        } else {
            return 0;
        }
    }
}
