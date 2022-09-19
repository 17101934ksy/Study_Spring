package org.example.jpabook.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Locker {

    @Id @GeneratedValue
    private Long id;

    private String name;
}
