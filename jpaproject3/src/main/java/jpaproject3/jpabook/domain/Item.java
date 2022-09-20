package jpaproject3.jpabook.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn
public abstract class Item extends BaseEntity{

    @Id @GeneratedValue
    @Column(name="item_id")
    private Long itemId;

    private String name;
    private Integer price;
    private Integer stuckQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
    public Item(){}

    public Item(String name, Integer price, Integer stuckQuantity){
        this.name = name;
        this.price = price;
        this.stuckQuantity = stuckQuantity;
    }


    /*
    * getter setter
    * */

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStuckQuantity() {
        return stuckQuantity;
    }

    public void setStuckQuantity(Integer stuckQuantity) {
        this.stuckQuantity = stuckQuantity;
    }
}
