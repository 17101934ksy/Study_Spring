package kakaoToy1.kakaoToy1.controller;

import kakaoToy1.kakaoToy1.domain.Member;
import kakaoToy1.kakaoToy1.dto.MemberRegisterRequestDTO;
import kakaoToy1.kakaoToy1.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/update/member")
public class MemberUpdateController {

    private final MemberRepository memberRepository;


    public MemberUpdateController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("")
    public String registerPage(Model model, MemberRegisterRequestDTO registerRequestDTO) {

        model.addAttribute("requestDto", registerRequestDTO);
        return "update-member";
    }

    @PostMapping("/data")
    public String updateMemberData(@ModelAttribute("requestDto") MemberRegisterRequestDTO rto, Model model, HttpServletRequest request){

        HttpSession session = request.getSession();

        System.out.println("session = " + session.getAttribute("sessionId"));

        Optional<Member> member = memberRepository.findByLoginId((String) session.getAttribute("sessionId"));

        System.out.println("member = " + member);
        System.out.println("rto.getUsername() = " + rto.getUsername());
        System.out.println("rto.getUsername() = " + rto.getJob());
        System.out.println("rto.getUsername() = " + rto.getUniqueId());
        System.out.println("rto.getUsername() = " + rto.getUniversity());
        System.out.println("rto.getUsername() = " + rto.getDepartment());
        System.out.println("rto.getUsername() = " + rto.getEmail());

        if (member.isPresent()){
            member.get().changeMemberData(rto.getUsername(), rto.getJob(), rto.getUniqueId(),
                    rto.getUniversity(), rto.getDepartment(), rto.getEmail());
            memberRepository.flush();

            return "index";
        }
        return "404";
    }

}
