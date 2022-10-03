package kakaoToy1.kakaoToy1.domain;

import kakaoToy1.kakaoToy1.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TestSaveTest {

    @Test
    public void 시험저장() throws Exception {
        //given
        Member member = new Member();
        TestProblem testProblem = new TestProblem();
        TestSpace testSpace = new TestSpace();

        //when
        TestSave testSave = new TestSave(10, "testResult", testProblem);

        //then
        assertThat(testSave.getAnswer()).isEqualTo("testResult");
    }

    @Test
    public void 시험답변경() throws Exception {
        //given
        Member member = new Member();
        TestProblem testProblem = new TestProblem();
        TestSpace testSpace = new TestSpace();
        TestSave testSave = new TestSave(10, "testResult", testProblem);
        TestSave testSave2 = new TestSave(10, "testResult", testProblem);

        //when
        testSave.changeAnswer("resultTest");

        //then
        assertThat(testSave.getAnswer()).isEqualTo("resultTest");
        assertThat(testSave2.getCreateAndModifyDate().getModifiedDate()).isNull();
        assertThat(testSave.getCreateAndModifyDate().getModifiedDate()).isAfter(testSave2.getCreateAndModifyDate().getCreatedDate());
    }

}