package toy1.toy1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toy1.toy1.aop.TimeTraceAop;
import toy1.toy1.repository.JpaMemberRepository;
import toy1.toy1.repository.MemberRepository;
import toy1.toy1.repository.MemoryMemberRepository;
import toy1.toy1.service.MemberService;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService(){ return new MemberService(memberRepository); }

//    @Bean
//    public TimeTraceAop timeTraceAop(){
//        return new TimeTraceAop();
//    }

//    @Bean
//    public MemberRepository memberRepository(){ return new JpaMemberRepository(em); }
}
