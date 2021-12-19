package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")//이걸하면 이제 구분할때 book 이면 어떻고 movie면 어떻고~
public abstract class Item {

    /**
     * Item을 상속시켜서 Book/Album/Movie 만들거다
     * 상속관계 매핑이기 때문에 상속관계 전략을 지정해야 한다. -> 이걸 부모 테이블에서 지정해줘야 한다.
     * 우리는 싱글테이블을 사용하니 @Inheritance(strategy = InheritanceType.SINGLE.TABLE) 로 한다.
     */

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//


    /**
     * 재고 증가
     * @param quantity
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /**
     * 재고 감소
     * @param quantity
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }


}
