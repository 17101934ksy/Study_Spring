package org.example.singletable.domain.item;

import org.example.Item;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;

@Entity
@DiscriminatorColumn()
public class Album extends Item {
    private String artist;
    private String etc;

    /*
    * getter setter
    * */

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }
}
