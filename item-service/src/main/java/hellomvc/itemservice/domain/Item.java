package hellomvc.itemservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {}

    @Builder
    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public void initId(Long id) {
        this.id = id;
    }

    public void updateItemName(String itemName) {
        this.itemName = itemName;
    }

    public void updatePrice(Integer price) {
        this.price = price;
    }

    public void updateQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}

