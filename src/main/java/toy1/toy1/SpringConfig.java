package toy1.toy1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toy1.toy1.repository.MemberRepository;
import toy1.toy1.repository.MemoryMemberRepository;
import toy1.toy1.service.MemberService;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
