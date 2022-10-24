package jpabook.jpashop.repository.order.simplequery;

import jpabook.jpashop.domain.Address;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderStatus;
    private Address address;
    private List<OrderItemQueryDto> orderItems;
}
