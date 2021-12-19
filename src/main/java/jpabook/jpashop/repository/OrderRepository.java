package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    /**
     * Querydsl 으로 통한 동적쿼리 해결 -> 여러 방법중에 가장 강력하다.
     * JPA Criteria 를 사용해도 코드만 보고 어떤 쿼리가 작성되는지 떠오르지 않는다 -> 유지보수성이 떨어진다.
     */
    public List<Order> findAll(OrderSearch orderSearch) {
        return null;
    }

}
