package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    /**
     * item 이 저장이 된적이 없으면 id를 가지고 있지 않는다.
     * 뭔가 아이템 값이 있다면 디비에서 가져온 값이다 -> 이떄는 update라고 생각을 일단 하자
     * @param item
     */
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else{
            em.merge(item);

            /**
             * Item merge = em.merge(item)
             * merge를 하면 아래 코드처럼 반환 한다고 보면 된다. 여기서는 merge가 영속성 관리를 받는 객체이다.
             *     @Transactional
             *     public Item updateItem(Long itemId, Book param) {
             *         Item findItem = itemRepository.findOne(itemId);
             *         findItem.setPrice(param.getPrice());
             *         findItem.setName(param.getName());
             *         findItem.setStockQuantity(param.getStockQuantity());
             *         return findItem;
             *     }
             */


        }
    }



    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }



}
