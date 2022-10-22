package jpabook.jpashop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member2 {

    @Id @GeneratedValue
    @Column(name = "member2_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private Order2 order2;
}
