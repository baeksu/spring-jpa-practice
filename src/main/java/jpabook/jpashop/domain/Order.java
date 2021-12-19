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

    protected Order(){}

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)//lazy로 설정하는게 좋다.
    @JoinColumn(name = "member_id")//외래키가 member_id 가 된다.
    private Member member;

    /**
     * cascade를 하면 orderItem 을 각각 persist 할때 나머지 코드들 persist를 한번만 코드 작성 하면된다.?? 정확한건 한번 찾아보자
     */
    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    /**
     * 1대1 관계에서는 외래키를 아무곳에나 둬도 되는데 주로 접근을 많이 하는곳에 한다.
     * 여기선 order를 통해서 delivery를 보므로 order에 두자
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;



    //order_date 로 컬럼등록이 된다.
    private LocalDateTime orderDate;//자바-8 에 있는 시간을 저장하는 클래스 인듯 , 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status;//주문 상태 [ORDER, CANCEL]

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);//양방향으로 넣어줄려고
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==주문 생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        //... 문법으로 OrderItem을 여러개 넘길 수 있도록 해줬다.
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //== 비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel(){
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();//상품 재고수량을 다시 원복해준다.
        }
    }

    //==조회 로직==//

    /**
     * 전체 주문 가격 조회
     * @return
     */
    public int getTotalPrice(){
//        int totalPrice = 0;
//        for (OrderItem orderItem : orderItems) {
//            totalPrice += orderItem.getTotalPrice();//수량*가격을 리턴해준다
//        }
//        return totalPrice;

        //스트림을 쓰면 다음과 같이 줄일 수 있다.
        int totalPrice = orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
        //수량*가격을 리턴해준다
        return totalPrice;
    }





}
