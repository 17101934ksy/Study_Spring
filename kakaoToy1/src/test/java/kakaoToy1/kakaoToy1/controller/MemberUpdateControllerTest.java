package kakaoToy1.kakaoToy1.controller;

import kakaoToy1.kakaoToy1.domain.Member;
import kakaoToy1.kakaoToy1.domain.MemberLoginWay;
import kakaoToy1.kakaoToy1.dto.MemberRegisterRequestDTO;
import kakaoToy1.kakaoToy1.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class MemberUpdateControllerTest {

    @Autowired MemberRepository memberRepository;

    @Test
    public void 유저정보수정() throws Exception {
        //given
        Member member = new Member(MemberLoginWay.KAKAO, "10000");
        Member saveMember = memberRepository.save(member);

        //when

        //then

    }

}