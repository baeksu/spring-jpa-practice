package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    /**
     * 여기는 단순히 리포지토리에 있는 메서드들을 사용하는거라
     * 컨트롤 부분에서 직접 접근을 해도 된다. -> 구조를 어떻게 할지는 협의필요
     */

    private final ItemRepository itemRepository;

    @Transactional// 얘는 저장을 해야하까 readOnly = false 로 해준다
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
