package org.example.domain.items;

import org.example.domain.Item;

import javax.persistence.Entity;

@Entity
public class Book extends Item {

    private String author;
    private String isbn;

    /*
    * getter setter
    * */

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
