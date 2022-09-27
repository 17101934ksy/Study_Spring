package study1.springboot1.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study1.springboot1.discount.DiscountPolicy;
import study1.springboot1.member.Member;
import study1.springboot1.member.MemberRepository;

@Component
public class OderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;


    public OderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Orders createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        return new Orders(member.getId(), itemName, itemPrice, discountPolicy.discount(member, itemPrice));
    }
}
