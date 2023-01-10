package jpabook.jpashop.repository.order.simplequery;

import lombok.Data;

@Data
public class OrderItemQueryDto {

    private String itemName;
    private int orderPrice;
    private int count;

}
