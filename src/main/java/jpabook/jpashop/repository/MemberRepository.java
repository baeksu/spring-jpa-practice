package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext//이걸 붙히면 스프링이 엔티티 매니저를 만들어서 주입해준다.
    private EntityManager em;


    //영속성 컨텍스트에 맴버객체를 넣고 나중에 트랜잭션이 커밋 되는순간에 insert 쿼리가 날아간다.
    public void save(Member member) {
        em.persist(member);
    }

    //첫번쨰 타입, 뒤에 pk
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    //from이 테이블이 아니라 객체가 된다.
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    //이름으로 검색할 때 -> setParameter() 가 어떤거지... jpa 강의 빨리 들어봐야겠다.
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}


