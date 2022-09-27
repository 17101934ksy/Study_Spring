package toy2.toy2.domain;

import toy2.toy2.service.discount.DiscountPolicy;

public class Orders {

    private Long itemId;
    private String itemName;
    private int price;

    private int discountPrice;
    private Long memberId;

    public Orders(Long itemId, Long memberId, String itemName, int itemPrice, int discountPrice) {
        this.itemId = itemId;
        this.memberId = memberId;
        this.itemName = itemName;
        this.price = itemPrice;
        this.discountPrice = discountPrice;
    }

    public void calculateDiscount(){
        this.price = price - discountPrice;
    }

    public int getDiscount(){
        return price;
    }


    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
