package study1.springboot1.order;

public interface OrderService {
    Orders createOrder(Long memberId, String itemName, int itemPrice);
}
