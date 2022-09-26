package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StatefulService2Test {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(StatefulService2Test.TestConfig.class);
        StatefulService2 bean1 = ac.getBean(StatefulService2.class);
        StatefulService2 bean2 = ac.getBean(StatefulService2.class);

        int price1 = bean1.order("userA", 10000);
        int price2 = bean2.order("userB", 20000);


        assertThat(price1).isEqualTo(10000);
        assertThat(price2).isEqualTo(20000);
    }

    static class TestConfig {
        @Bean
        public StatefulService2 statefulService(){
            return new StatefulService2();
        }
    }

}