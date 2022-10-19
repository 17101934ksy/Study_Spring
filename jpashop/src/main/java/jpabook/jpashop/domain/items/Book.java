package jpabook.jpashop.domain.items;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter @Setter
public class Book extends Item{

    private String author;
    private String isbn;

    public Book() {}
    public Book(String name, int price, int quantity) {
        super(name, price, quantity);
    }

}
