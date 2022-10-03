package kakaoToy1.kakaoToy1.domain;

import kakaoToy1.kakaoToy1.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;
import static kakaoToy1.kakaoToy1.domain.TestSubject.PYTHONTEST;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TestProblemTest {

    @Test
    public void 시험문제생성() throws Exception {
        //given
        Member member = new Member();
        TestSave testSave = new TestSave();

        TestProblem testProblem = new TestProblem(PYTHONTEST, 4010101L, 400000L, now(), 1, "test1", member);
        TestProblem testProblem2 = new TestProblem(PYTHONTEST, 401100L, 39999L, now(), 2, "test2", member);

        //when
        testProblem2.addTestSaves(testSave);
        testProblem2.addTestSaves(testSave);
        testProblem.changeTestDate(now());

        //then
        assertThat(testProblem.getTestSubject()).isEqualTo(PYTHONTEST);
        assertThat(testProblem.getCreateAndModifyDate().getCreatedDate()).isNotNull();
        assertThat(testProblem.getCreateAndModifyDate().getCreatedDate()).isBefore(now());
        assertThat(testProblem2.getTestSaves().size()).isEqualTo(2);
    }

    @Test
    public void 시험날짜변경() throws Exception {
        //given
        Member member = new Member();

        TestProblem testProblem = new TestProblem(PYTHONTEST, 4010101L, 400000L, now(), 1, "test1", member);
        TestProblem testProblem2 = new TestProblem(PYTHONTEST, 401100L, 39999L, now(), 2, "test2", member);

        //when
        testProblem.changeTestDate(now());

        //then
        assertThat(testProblem.getCreateAndModifyDate().getModifiedDate()).isNotEqualTo(testProblem2.getCreateAndModifyDate().getModifiedDate());
        assertThat(testProblem2.getCreateAndModifyDate().getModifiedDate()).isNull();

    }

    @Test
    public void 시험문제변경() throws Exception {
        //given
        Member member = new Member();

        TestProblem testProblem = new TestProblem(PYTHONTEST, 4010101L, 400000L, now(), 1, "test1", member);
        TestProblem testProblem2 = new TestProblem(PYTHONTEST, 401100L, 39999L, now(), 2, "test2", member);


        //when
        testProblem.changeProblem("test3");
        testProblem2.changeProblem("test1224");
        testProblem.changeProblem("test3");

        //then
        assertThat(testProblem.getProblem()).isEqualTo("test3");
        assertThat(testProblem2.getProblem()).isEqualTo("test1224");
        assertThat(testProblem.getCreateAndModifyDate().getModifiedDate()).isAfter(testProblem2.getCreateAndModifyDate().getModifiedDate());
    }

    @Test
    public void 시험교과번호변경() throws Exception {
        //given
        Member member = new Member();

        TestProblem testProblem = new TestProblem(PYTHONTEST, 4010101L, 400000L, now(), 1, "test1", member);
        TestProblem testProblem2 = new TestProblem(PYTHONTEST, 401100L, 39999L, now(), 2, "test2", member);


        //when
        testProblem.changeSubjectNumber(12000L);

        //then
        assertThat(testProblem.getSubjectNumber()).isEqualTo(12000L);
        assertThat(testProblem2.getCreateAndModifyDate().getModifiedDate()).isNull();
    }

    @Test
    public void 시험분반번호변경() throws Exception {
        //given
        Member member = new Member();

        TestProblem testProblem = new TestProblem(PYTHONTEST, 4010101L, 400000L, now(), 1, "test1", member);
        TestProblem testProblem2 = new TestProblem(PYTHONTEST, 401100L, 39999L, now(), 2, "test2", member);

        //when
        testProblem.changeClassNumber(100000l);

        //then
        assertThat(testProblem.getClassNumber()).isEqualTo(100000L);
        assertThat(testProblem2.getClassNumber()).isEqualTo(39999L);
        assertThat(testProblem2.getCreateAndModifyDate().getModifiedDate()).isNull();

    }

    @Test
    public void 시험과목변경() throws Exception {
        //given
        Member member = new Member();

        TestProblem testProblem = new TestProblem(PYTHONTEST, 4010101L, 400000L, now(), 1, "test1", member);
        TestProblem testProblem2 = new TestProblem(PYTHONTEST, 401100L, 39999L, now(), 2, "test2", member);


        //when
        testProblem.changeTestSubject(TestSubject.JAVATEST);

        //then
        assertThat(testProblem.getTestSubject()).isEqualTo(TestSubject.JAVATEST);
        assertThat(testProblem2.getTestSubject()).isEqualTo(PYTHONTEST);
        assertThat(testProblem2.getCreateAndModifyDate().getModifiedDate()).isNull();
        assertThat(testProblem.getCreateAndModifyDate().getModifiedDate()).isAfter(testProblem2.getCreateAndModifyDate().getCreatedDate());
    }

    @Test
    public void 시험번호변경() throws Exception {
        //given
        Member member = new Member();

        TestProblem testProblem = new TestProblem(PYTHONTEST, 4010101L, 400000L, now(), 1, "test1", member);
        TestProblem testProblem2 = new TestProblem(PYTHONTEST, 401100L, 39999L, now(), 2, "test2", member);


        //when
        testProblem.changeTestNumber(3);

        //then
        assertThat(testProblem.getTestNumber()).isEqualTo(3);
        assertThat(testProblem2.getTestNumber()).isEqualTo(2);
        assertThat(testProblem2.getCreateAndModifyDate().getModifiedDate()).isNull();
        assertThat(testProblem.getCreateAndModifyDate().getModifiedDate()).isAfter(testProblem2.getCreateAndModifyDate().getCreatedDate());
    }
}