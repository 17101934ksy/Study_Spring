package jpaproject3.jpabook.domain;

import javax.persistence.*;

@Entity
@Table(name="locker")
public class Locker {

    @Id @GeneratedValue
    @Column(name="lock_id")
    private Long id;

    private String name;

    @OneToOne(mappedBy = "locker")
    private Member member;
}
