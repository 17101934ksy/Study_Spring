package hellomvc.itemservice.domain;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong(0);

    public Item save(Item item) {
        item.initId(sequence.incrementAndGet());
        store.put(item.getId(), item);

        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new LinkedList<>(store.values());
    }

    public void update(Long itemId, ItemDto updateParam) {
        Item item = findById(itemId);

        if (updateParam.getItemName() != null) {
            item.updateItemName(updateParam.getItemName());
        }
        if (updateParam.getPrice() != null) {
            item.updatePrice(updateParam.getPrice());
        }
        if (updateParam.getQuantity() != null) {
            item.updateQuantity(updateParam.getQuantity());
        }
    }

    public void clearStore() {
        store.clear();
    }

    public void delete(Long itemId) {
        store.remove(itemId);
    }
}
