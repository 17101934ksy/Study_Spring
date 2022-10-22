package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.OrderSimpleDto;
import jpabook.jpashop.repository.order.OrderDto;
import lombok.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

//    @GetMapping("/api/v2/orders-one")
//    public OrderResult ordersV2One(@RequestParam("orderId") Long id) {
//        return OrderResult.builder()
//                .orderSize(1)
//                .jsonData(orderRepository.findOne(id))
//                .build();
//    }


    @GetMapping("/api/v2/orders")
    public OrderResult ordersV2() {
        List<OrderDto> order = orderRepository
                .findAllByString(new OrderSearch())
                .stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return OrderResult.builder()
                .orderSize(order.size())
                .jsonData(order)
                .build();
    }

    @GetMapping("/api/v3/orders")
    public OrderResult ordersV3() {
        List<OrderDto> orderDto = orderRepository.findAllWithItems()
                .stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return OrderResult.builder()
                .orderSize(orderDto.size())
                .jsonData(orderDto)
                .build();
    }

    @GetMapping("/api/v3/distinct/orders")
    public OrderResult ordersDistinctV3() {
        List<OrderDto> orderDto = orderRepository.findAllWithDistinctItems()
                .stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return OrderResult.builder()
                .orderSize(orderDto.size())
                .jsonData(orderDto)
                .build();
    }


    @Builder @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static class OrderResult<T> {
        private int orderSize;
        private T jsonData;

    }

}
