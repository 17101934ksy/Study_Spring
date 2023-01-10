package jpabook.jpashop.domain.items;

import jpabook.jpashop.controller.BookForm;
import lombok.Builder;
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


    @Builder
    public Book(String name, int price, int stockQuantity, String author, String isbn) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }

    public void updateBook(BookForm bookForm) {
        this.name = bookForm.getName();
        this.price = bookForm.getPrice();
        this.stockQuantity = bookForm.getStockQuantity();
        this.author = bookForm.getAuthor();
        this.isbn = isbn;
    }
}
