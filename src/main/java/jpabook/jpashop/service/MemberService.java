package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)//@Transactional(readOnly = true) 를 하면 성능에 도움이 된다
@RequiredArgsConstructor// 이걸 쓰면 final 이 있는 필드만 가지고 생성자를 만들어준다.
                        // + 스프링에서 디폴트로 생성자가 하나만 있는 경우는 자동으로 생성자 주입을 해준다
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional//디폴트가 readOnly=false 이다.
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원가입

     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }



}
