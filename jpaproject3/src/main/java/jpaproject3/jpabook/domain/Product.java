package jpaproject3.jpabook.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "products")
    private List<MemberProduct>memberProducts = new ArrayList<>();

}
