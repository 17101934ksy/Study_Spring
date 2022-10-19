package hellomvc.itemservice.web.basic;

import hellomvc.itemservice.domain.Item;
import hellomvc.itemservice.domain.ItemDto;
import hellomvc.itemservice.domain.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/basic/items")
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 1000, 20));
        itemRepository.save(new Item("itemB", 20000, 15));
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    //ModelAttribute의 속성(모델 객체를 만듬, view 모델에 넣어주는 역할 수행) 프로퍼티 접근법
//    @PostMapping("/add")
//    public String addItemV1(@ModelAttribute("item") ItemDto itemDto, Model model) {
//
//        Item item = Item.builder()
//                .itemName(itemDto.getItemName())
//                .price(itemDto.getPrice())
//                .quantity(itemDto.getQuantity())
//                .build();
//
//        itemRepository.save(item);
//        model.addAttribute("item", item); //자동 추가, 생략 가능
//
//        return "basic/item";
//    }


    @PostMapping("/add")
    public String addItemV1(@ModelAttribute("item") ItemDto itemDto, Model model,
                            RedirectAttributes redirectAttributes) {

        Item item = Item.builder()
                .itemName(itemDto.getItemName())
                .price(itemDto.getPrice())
                .quantity(itemDto.getQuantity())
                .build();

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        model.addAttribute("item", item); //자동 추가, 생략 가능

        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editPostForm(@PathVariable long itemId,
                               @ModelAttribute("item") ItemDto itemDto,
                               RedirectAttributes redirectAttributes) {

        itemRepository.update(itemId, itemDto);

        redirectAttributes.addAttribute("itemId", itemId);
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/delete")
    public String deletePostForm(@PathVariable Long itemId) {

        log.info("itemId = {}", itemId);

        itemRepository.delete(itemId);

        return "redirect:/basic/items";
    }



}
