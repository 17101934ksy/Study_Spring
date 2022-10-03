package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username")
    private String name;

    @Embedded
    private Address address;

    // orders 테이블의 member 필드 !!!
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
