package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.*;


class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService bean1 = ac.getBean(StatefulService.class);
        StatefulService bean2 = ac.getBean(StatefulService.class);

        bean1.order("userA", 10000);
        bean2.order("userB", 20000);

        //ThreadA: 사용자 A가 주문 금액을 조회

        int price = bean1.getPrice();
        System.out.println("price = " + price);

        assertThat(bean1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }

}