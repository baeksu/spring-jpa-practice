package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * Member가 이미 있는데 Form을 따로 만드는 이유는
 * Form화면에 입력하는 것과 도메인의 Member와는 차이가 있다.
 * Member 부분 코드가 너무 지저분해질 수 있어서
 *
 */
@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;

}
