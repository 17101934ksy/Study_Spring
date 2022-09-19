package jpaproject3.jpabook.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
public class Orders {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus Status;

    @OneToMany(mappedBy="order")
    List<OrderItem> orderItemList = new ArrayList<>();

    public Orders(){}

    public Orders(Member member, LocalDateTime orderDate, OrderStatus status){
        this.member = member;
        this.orderDate = orderDate;
        this.Status = status;
    }

    /*
    * custom method
    * */



    /*
    * getter setter
    * */

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return Status;
    }

    public void setStatus(OrderStatus status) {
        Status = status;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void addOrderItemList(OrderItem orderItem) {

        orderItemList.add(orderItem);
        orderItem.setOrder(this);


    }
}
