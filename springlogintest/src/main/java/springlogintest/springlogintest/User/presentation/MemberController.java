package springlogintest.springlogintest.User.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springlogintest.springlogintest.User.domain.Member;
import springlogintest.springlogintest.User.infrastructure.persistence.MemberRepository;

import java.util.Optional;

@Slf4j
@RestController
public class MemberController {

    private MemberRepository memberRepository;

    @GetMapping("/api/members/{memberId}")
    public Optional<Member> get(@PathVariable("memberId") Long id){
        return memberRepository.findById(id);
    }

    /**
     * Content-Type : 헤더 기반 추가 매핑 Media type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*",
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    @PostMapping(value="/mapping-consume", consumes = "application/json")
    public String mappingConsumes(){
        log.info("mappingConsumes");
        return "ok";
    }

}
