package hello.core.order;

public interface OrderService {
    Orders createOrder(Long memberId, String itemName, int itemPrice);
}
