package kakaoToy1.kakaoToy1.controller;

import kakaoToy1.kakaoToy1.domain.member.Member;
import kakaoToy1.kakaoToy1.domain.member.MemberLoginWay;
import kakaoToy1.kakaoToy1.repository.MemberRepository;
import kakaoToy1.kakaoToy1.service.KaKaoService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class KaKaoController {

    @Autowired KaKaoService ks;

    private final MemberRepository memberRepository;

    public KaKaoController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/login")
    public String loginPage()
    {
        return "kakaoCI/login";
    }

    @GetMapping("")
    public String getCI(@RequestParam String code, Model model, HttpServletRequest request) throws IOException {

        String access_token = ks.getToken(code);
        Map<String, Object> userInfo = ks.getUserInfo(access_token);
        model.addAttribute("code", code);
        model.addAttribute("access_token", access_token);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("loginId", (String) userInfo.get("id"));

        Member member = new Member(MemberLoginWay.KAKAO, (String) userInfo.get("id"));

        Optional<Member> memberByLoginId = memberRepository.findByLoginId(member.getLoginId());

        if (memberByLoginId.isEmpty()){
            Member saveMember = memberRepository.save(member);
        }

        //로그인 성공 처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        session.setAttribute("code", code);
        session.setAttribute("access_token", access_token);
        session.setAttribute("sessionId", member.getLoginId());

        System.out.println("session = " + session);

        //ci는 비즈니스 전환후 검수신청 -> 허락받아야 수집 가능
        return "kakaoCI/logout";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null){
            ks.getLogout((String) session.getAttribute("access_token"));
            session.invalidate();
        }

        return "index";
    }

}

