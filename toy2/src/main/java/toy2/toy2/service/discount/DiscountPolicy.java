package toy2.toy2.service.discount;

import toy2.toy2.domain.Member;

public interface DiscountPolicy {
    /**
     * @return 할인 정책
     */

    int discount(Member member, int price);
}
