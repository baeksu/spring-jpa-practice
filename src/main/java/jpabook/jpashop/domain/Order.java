package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")//관례상 orders로 한다. sql에서 ORDER 떄문에
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")//외래키가 member_id 가 된다.
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    /**
     * 1대1 관계에서는 외래키를 아무곳에나 둬도 되는데 주로 접근을 많이 하는곳에 한다.
     * 여기선 order를 통해서 delivery를 보므로 order에 두자
     */
    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;


    private LocalDateTime orderDat;//자바-8 에 있는 시간을 저장하는 클래스 인듯 , 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status;//주문 상태 [ORDER, CANCEL]



}
