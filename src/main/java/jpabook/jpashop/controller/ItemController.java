package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        Book book = new Book();
        //아래 set들을 원래는 생성자 or 함수로 따로 빼는게 좋다.
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")//{} 안에 값은 바뀔 수 있는거고 @PathVariable이랑 매핑되는듯
    public String update(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);//예제를 단순화 하기 위해서 여기선 캐스팅 했다

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(form.getName());
        form.setPrice(form.getPrice());
        form.setStockQuantity(form.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {
        /**
         * 해커(?) 가 의도적으로 다른사람을 itemId로 넘겨서 다른사람의 정보를 막 변경할 수 있다.
         * 이런게 보안상 문제가 많이 된다. 서비스 계층이나 뒷단이나 앞단에서
         * 해당 유저에게 권한이 있는지 체크하는 로직이 필요하다
         */
//        Book book = new Book();
//        book.setId(form.getId());
//        book.setName(form.getName());
//        book.setPrice(form.getPrice());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setAuthor(form.getAuthor());
//        book.setIsbn(form.getIsbn());
        //        itemService.saveItem(book);
        //위에처럼 객체를 여기서 생성하는거 보다 아래처럼 하는게 훨씬 깔끔하고 필요한 데이터만 넘겨서 수정한다.

        itemService.updateItem(itemId,form.getName(),form.getPrice(),form.getStockQuantity());

        return "redirect:/items";//itemRepository의 em.merge와 상관이 있는거 같은데... "변경 감지와 병합" 주요
    }
}
