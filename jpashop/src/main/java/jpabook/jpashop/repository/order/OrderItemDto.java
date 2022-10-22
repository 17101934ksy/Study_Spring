package jpabook.jpashop.repository.order;

import jpabook.jpashop.domain.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemDto {

    private String itemName; //상품명
    private int orderPrice; //주문 가격
    private int count; //주문 수량

    public OrderItemDto(OrderItem orderItem) {
        this.itemName = orderItem.getItem().getName();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
        System.out.println("================================================");
        System.out.println(" order Item을 통과합니다 ");
        System.out.println("================================================");
    }
}
