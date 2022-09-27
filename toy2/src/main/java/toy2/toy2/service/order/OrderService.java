package toy2.toy2.service.order;

import toy2.toy2.domain.Orders;
import toy2.toy2.service.discount.DiscountPolicy;

public interface OrderService {
    Orders createOrder(Long itemId, Long memberId, String itemName, int itemPrice, int discountPrice);

}