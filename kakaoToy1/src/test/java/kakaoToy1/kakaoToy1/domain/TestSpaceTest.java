package kakaoToy1.kakaoToy1.domain;

import kakaoToy1.kakaoToy1.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.hibernate.annotations.Entity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TestSpaceTest {

    @Test
    public void 시험장소생성장() throws Exception {
        //given
        Member member = new Member();

        //when
        TestSpace testSpace = new TestSpace("제1시험장", 300, member);

        //then
        assertThat(testSpace.getName()).isEqualTo("제1시험장");
    }

    @Test
    public void 최대인원변경() throws Exception {
        //given
        TestSpace testSpace = new TestSpace("제1시험장", 140, new Member());
        TestSpace testSpace2 = new TestSpace("제1시험장", 140, new Member());

        //when
        testSpace.changeMaxMember(150);

        //then
        assertThat(testSpace.getMaxMember()).isEqualTo(150);
        assertThat(testSpace2.getCreateAndModifyDate().getModifiedDate()).isNull();
        assertThat(testSpace.getCreateAndModifyDate().getModifiedDate()).isAfter(testSpace2.getCreateAndModifyDate().getCreatedDate());
    }

    @Test
    public void 시험장이름변경() throws Exception {
        //given
        TestSpace testSpace = new TestSpace("제1시험장", 140, new Member());
        TestSpace testSpace2 = new TestSpace("제1시험장", 140, new Member());

        //when
        testSpace.changeName("제2시험장");

        //then
        assertThat(testSpace.getName()).isEqualTo("제2시험장");
        assertThat(testSpace2.getCreateAndModifyDate().getModifiedDate()).isNull();
        assertThat(testSpace.getCreateAndModifyDate().getModifiedDate()).isAfter(testSpace2.getCreateAndModifyDate().getCreatedDate());
    }

}