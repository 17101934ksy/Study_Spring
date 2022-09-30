package kakaoToy1.kakaoToy1.controller;

import kakaoToy1.kakaoToy1.domain.Member;
import kakaoToy1.kakaoToy1.dto.MemberRegisterRequestDTO;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class MemberRegisterController {

    @GetMapping("")
    public String registerPage(Model model, MemberRegisterRequestDTO registerRequestDTO) {

        model.addAttribute("requestDto", registerRequestDTO);
        return "register-member";
    }
//
//    @PostMapping("/kakao")
//    @Transactional
//    public String registerKaKao(@ModelAttribute("requestDto") MemberRegisterRequestDTO rto, Model model){
//        MemberRepository memberRepository = new MemberRepository();
//        Member member = memberRepository.find((Long) model.getAttribute("code"));
//        member.changeMemberData(rto.getUsername(), rto.getJob(), rto.getStudentId(),rto.getProfessorId(),
//                rto.getUniversity(), rto.getDepartment(), rto.getEmail());
//        return "";
//    }
}
