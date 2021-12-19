package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * 이 화면에서 submit을 누르면 똑같은 url로 post 요청을 한다.
     * 이떄 memberform 도 넘겨준다
     */
    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    /**
     * MemberForm 을 보면 NotEmpty가 붙혀져 있는데
     *
     * @Valid를 붙히게 되면 스프링이 validation 을 해준다.
     * @Valid 를 붙힌 후에 매개변수로 BindingResult가 오게 되면 오류를 가지고서 아래를 실행한다
     * 원래는 잘못된 형식이 넘어오면 아래코드를 실행하면 안된다.
     */
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";//잘못된 형식으로 오면 양식으로 다시 보내준다.
            // "회원 이름은 필수 입니다." 를 뛰어주면서...
            // 근데 이건 타임리프도 좀 알아야지 나중에 써먹을 수 있을듯
            // 타임리프 field-error 부분 한번 보기
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";//이렇게 하면 회원 가입 후 첫번째 페이지로 넘어가게 된다.
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
