package jpabook.jpashop.domain;

import lombok.*;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "orders", cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList();

    @OneToOne(cascade = CascadeType.ALL, fetch = LAZY)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    @Setter
    private LocalDateTime orderDate;


    @Enumerated(EnumType.STRING)
    private OrderStatus status; // Order, Cancel

    //==연관관계 편의 메서드
    private void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    private void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrders(this);
    }

    private void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }

    //== 생성 메서드 ==//



    public static Order createOrder(Member member, Delivery delivery,
                                    OrderItem... orderItems) {

        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.updateOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가합니다");
        }

        this.updateOrderStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //== 조회 로직==//

    /**
     * 전체 주문 가격 조회
     * @return
     */
    public int getTotalPrice() {
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice)
                .sum();

        //        int totalPrice = 0;
        //        for (OrderItem orderItem : orderItems) {
        //            totalPrice += orderItem.getTotalPrice();
        //        }


    }


}
