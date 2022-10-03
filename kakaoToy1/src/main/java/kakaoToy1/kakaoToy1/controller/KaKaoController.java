package kakaoToy1.kakaoToy1.controller;

import kakaoToy1.kakaoToy1.domain.member.Member;
import kakaoToy1.kakaoToy1.domain.member.MemberLoginWay;
import kakaoToy1.kakaoToy1.repository.MemberRepository;
import kakaoToy1.kakaoToy1.service.KaKaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/kakaologin")
@RequiredArgsConstructor
public class KaKaoController {

    private final KaKaoService kakaoService;
    private final MemberRepository memberRepository;

    @GetMapping("/login")
    public String loginPage()
    {
        return "kakaoCI/login";
    }

    @GetMapping("")
    public String getCI(@RequestParam String code, Model model, HttpServletRequest request) throws IOException {

        String access_token = kakaoService.getToken(code);
        Map<String, Object> userInfo = kakaoService.getUserInfo(access_token);

        model.addAttribute("code", code);
        model.addAttribute("access_token", access_token);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("loginId", (String) userInfo.get("id"));

        Member member = new Member(MemberLoginWay.KAKAO, (String) userInfo.get("id"));
        Optional<Member> memberByLoginId = memberRepository.findByLoginId(member.getLoginId());

        if (memberByLoginId.isEmpty()) {Member saveMember = memberRepository.save(member);}

        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        session.setAttribute("code", code);
        session.setAttribute("access_token", access_token);
        session.setAttribute("sessionId", member.getLoginId());

        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null){
            kakaoService.getLogout((String) session.getAttribute("access_token"));
            session.invalidate();
        }

        return "redirect:/";
    }

}

