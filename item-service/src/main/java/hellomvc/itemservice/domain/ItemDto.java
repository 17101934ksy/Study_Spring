package hellomvc.itemservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemDto {

    private String itemName;
    private Integer price;
    private Integer quantity;

    @Builder
    public ItemDto(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
