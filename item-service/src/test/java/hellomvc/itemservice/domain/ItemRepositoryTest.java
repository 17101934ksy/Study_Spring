package hellomvc.itemservice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    public void save() throws Exception {
        //given
        Item item = new Item("test", 1000, 10);

        //when
        Item savedItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);

    }

    @Test
    public void findAll() throws Exception {
        //given
        Item item1 = Item.builder().itemName("test1").price(10000).quantity(40).build();
        Item item2 = Item.builder().itemName("test2").price(7000).quantity(30).build();

        itemRepository.save(item1);
        itemRepository.save(item2);

        //when
        List<Item> findAllItems = itemRepository.findAll();

        //then
        assertThat(findAllItems.size()).isEqualTo(2);
        assertThat(findAllItems).contains(item1, item2);

    }

    @Test
    public void updateItem() throws Exception {
        //given
        Item item = Item.builder().itemName("test1").price(10000).quantity(40).build();

        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        //when
        ItemDto itemDto = ItemDto.builder().itemName("item2").price(24500).quantity(15).build();
        itemRepository.update(itemId, itemDto);

        //then
        Item findITem = itemRepository.findById(itemId);

        assertThat(findITem.getItemName()).isEqualTo(itemDto.getItemName());
        assertThat(findITem.getPrice()).isEqualTo(itemDto.getPrice());
        assertThat(findITem.getQuantity()).isEqualTo(itemDto.getQuantity());


    }


}