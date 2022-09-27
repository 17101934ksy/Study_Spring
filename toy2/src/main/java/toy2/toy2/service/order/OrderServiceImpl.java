package toy2.toy2.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import toy2.toy2.domain.Orders;
import toy2.toy2.service.discount.DiscountPolicy;
import toy2.toy2.domain.Member;
import toy2.toy2.repository.MemberRepository;

@Component
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Orders createOrder(Long itemId, Long memberId, String itemName, int itemPrice, int discountPrice) {
        Member member = memberRepository.findById(memberId);

        return new Orders(itemId, memberId, itemName, itemPrice, discountPrice);
    }
}
