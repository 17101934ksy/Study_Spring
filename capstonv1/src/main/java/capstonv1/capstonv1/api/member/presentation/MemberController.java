package capstonv1.capstonv1.api.member.presentation;

import capstonv1.capstonv1.api.member.application.service.MemberService;
import capstonv1.capstonv1.api.member.domain.Member;
import capstonv1.capstonv1.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ApiResponse getUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Member member = memberService.getMember(principal.getUsername());

        return ApiResponse.success("member", member);
    }

}
