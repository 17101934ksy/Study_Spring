package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        excludeFilters =  @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = Configuration.class)
        //  AppConfig 파일이 있으므로, 예제의 안정된 상태를 유지하기 위해 일단 넣은 것
)
public class AutoAppConfig {

}
