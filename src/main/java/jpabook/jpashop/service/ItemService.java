package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
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

    @Transactional
    public void updateItem(Long itemId, String name , int price , int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(price);//set으로 바꾸지 말고 change() 메서드를 하나 만들어서 변경하는게 훨씬 낫다.
                                    // 어디서 변경했는지 추적하기 쉽다.
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);

        /**
         * 변경감지를 사용하는게 훨씬 안전하고 권장된다.
         * 변경감지 기능 사용 -> 지금 itemController 에 있는 updateItem 은 merge기능을 사용한 건다.
         *  //itemRepository.save(findItem);
         *  이걸 해줄 필요가 없다 jpa에 의해서 findItem 은 영속상태이기 떄문에
         *  @Transactional 에 의해서 트랜잭션이 커밋이 된다.
         *  커밋이 되면 jpa는 flush를 날려서 영속성 엔티티 중에 변경된거를 찾아서 update쿼리를 쳐버린다
         */


    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
