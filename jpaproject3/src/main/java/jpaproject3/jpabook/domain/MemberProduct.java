package jpaproject3.jpabook.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
public class MemberProduct {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product products;

    @Column(name = "order_amount")
    private Integer orderAmount;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

}
