package kakaoToy1.kakaoToy1.repository;

import kakaoToy1.kakaoToy1.domain.member.School;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class SchoolRepositoryTest {

    @Autowired private SchoolRepository schoolRepository;

    @Test
    public void 학교저장() throws Exception {

        //given
        School school = new School(1L, "ttt");

        //when
        School newSchool = schoolRepository.saveAndFlush(school);

        //then

    }
}