package org.example.domain.items;

import org.example.domain.Item;

import javax.persistence.Entity;

@Entity
public class Movie extends Item {

    private String directors;
    private String actor;

    /*
    * getter setter
    * */

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}
