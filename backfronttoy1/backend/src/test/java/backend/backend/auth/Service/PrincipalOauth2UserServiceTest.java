package backend.backend.auth.Service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PrincipalOauth2UserServiceTest {

    @Test
    public void 로그인_테스트() throws Exception {
        //given

        String testName = "testname123";
        String test2 = "";
        //when

        switch (testName) {
            case "testname123":
                System.out.println("============================");
                System.out.println("case1");
                System.out.println("============================");
                test2 = "case1";
                break;

            case "testname1234":
                System.out.println("============================");
                System.out.println("case2");
                System.out.println("============================");
                test2 = "case2";
                break;
        }
        //then
        assertThat(test2).isEqualTo("case1");
    }

}