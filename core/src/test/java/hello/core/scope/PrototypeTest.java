package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PrototypeTest {

    @Test
    void prototypeBeanFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototypeBean1");
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);

        System.out.println("find prototypeBean2");
        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);

        Assertions.assertThat(bean1).isNotSameAs(bean2);

        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean{

        @PostConstruct
        public void init(){
            System.out.println("Prototype init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("Prototype close");
        }
    }
}
