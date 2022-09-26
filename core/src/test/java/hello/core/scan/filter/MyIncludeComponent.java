package hello.core.scan.filter;

import java.lang.annotation.*;

// type은 클래스 레벨에 붙는다
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {

}
