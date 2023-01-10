package jpabook.jpashop.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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


    public void updateName(String name) {
        this.name = name;
    }

    public void updateAddress(Address address) {
        this.address = address;
    }

    @Builder
    public Member(String name) {
        this.name = name;
    }

    public Member(String name, Address address) {
        this.name = name;
        this.address =address;
    }

}
