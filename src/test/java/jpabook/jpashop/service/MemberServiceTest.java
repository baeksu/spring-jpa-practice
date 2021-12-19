package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.hibernate.internal.ExceptionMapperStandardImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional//test 를 돌릴떄는 테스트가 끝나고 나서 roll-back 시켜준다.
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;



    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("경우");
        //when
        Long saveId = memberService.join(member);

        //then
        //@Transactional 을 붙히면 같은 영속성 어쩌고.. 같은 걸로 관리가 되는듯
        Assertions.assertEquals(member, memberRepository.findOne(saveId));



    }

    @Test
    public void 중복_회원_예외() throws Exception {

        //given
        Member member1 = new Member();
        member1.setName("경우");

        Member member2 = new Member();
        member2.setName("경우");

        //when
        memberService.join(member1);
        try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            return;
        }

        //then
        fail("예외가 발생해야 한다.");//여기 오면 안되는데 여기까지 오면 fail을 발생시킨다.


    }

}