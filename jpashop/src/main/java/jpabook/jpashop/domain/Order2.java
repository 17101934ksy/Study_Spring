package jpabook.jpashop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order2 {

    @Id @GeneratedValue
    @Column(name = "order2_id")
    private Long id;

    private String name;


}
