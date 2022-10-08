package backend.backend.domain;

import backend.backend.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
class UsersTest {

    @Autowired
    private UserRepository repository;

    @Test
    @Transactional
    public void 회원등록() throws Exception {

        //given
        Users user = new Users();

        //when
        repository.save(user);
        Optional<Users> findUser = repository.findById(1L);

        //then
        assertThat(findUser.get().getId()).isEqualTo(1L);
    }

    @Test
    @Transactional
    public void 회원이름변경() throws Exception {
        //given
        Users user = new Users();
        user.changeUserName("kkk");

        //when
        repository.save(user);
        Optional<Users> findUser = repository.findById(2L);

        //then
        if (findUser.isPresent()){
            assertThat(findUser.get().getId()).isEqualTo(2L);
            System.out.println("===================================================================");
            System.out.println("findUser.get().getUsername() = " + findUser.get().getUsername());
            System.out.println("===================================================================");
        } else {
            System.out.println("===================================================================");
            System.out.println("===================================================================");
        }

//        assertThat(findUser.get().getUsername()).isEqualTo("kkk");
//        System.out.println("===================================================================");
//        System.out.println("findUser.get().getUsername() = " + findUser.get().getUsername());
//        System.out.println("===================================================================");
    }

//    @Test
//    public void 회원나이변경() throws Exception {
//        //given
//        Users user = new Users();
//        user.changeAge(20);
//
//        //when
//        repository.save(user);
//        Optional<Users> findUser = repository.findById(1L);
//
//        //then
//        assertThat(findUser.get().getId()).isEqualTo(1L);
//        assertThat(findUser.get().getAge()).isEqualTo(20);
//    }

}