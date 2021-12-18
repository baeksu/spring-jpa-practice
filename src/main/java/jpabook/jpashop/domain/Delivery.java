package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Lazy;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;


    @OneToOne(mappedBy = "delivery" , fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;


    @Enumerated(EnumType.STRING)//enum을 쓸떄는 @Enumerated 를 써줘야 한다. -> String으로 설정하는게 좋다. / 새로운 enum이 추가되거나 할떄 장애가 발생할 수 있다.
    private DeliveryStatus status;//ready,comp

}
