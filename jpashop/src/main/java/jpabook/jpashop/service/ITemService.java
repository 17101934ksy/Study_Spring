package jpabook.jpashop.service;

import jpabook.jpashop.controller.BookForm;
import jpabook.jpashop.domain.items.Book;
import jpabook.jpashop.domain.items.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ITemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }


    @Transactional
    public void updateBook(Long itemId, BookForm param) {
        //영속 상태 -> 스프링의 트랜젝셔널에 의해 플러시 영속성 변경상태 확인 더티체크
        //commit 직전에 변경감지
        Book findItem = (Book) itemRepository.findOne(itemId);
        findItem.updateBook(param);
    }
}
