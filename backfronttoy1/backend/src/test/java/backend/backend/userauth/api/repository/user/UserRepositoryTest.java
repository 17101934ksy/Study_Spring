package backend.backend.userauth.api.repository.user;

import backend.backend.userauth.api.entity.user.User;
import backend.backend.userauth.oauth2.entity.ProviderType;
import backend.backend.userauth.oauth2.entity.RoleType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static backend.backend.userauth.oauth2.entity.ProviderType.GOOGLE;
import static backend.backend.userauth.oauth2.entity.RoleType.USER;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

    @SpyBean
    private UserRepository userRepository;

    @Test
    public void 유저리포지토리() throws Exception {
        //given

        User user = User.builder()
                .userId("ttt")
                .username("ttt")
                .email("ttttttt")
                .emailVerifiedYn("Y")
                .roleType(USER)
                .providerType(GOOGLE)
                .createdAt(now())
                .modifiedAt(now())
                .build();

        //when
        User saveUser = userRepository.saveAndFlush(user);

        //then
        assertThat(saveUser.getUserId()).isEqualTo(user.getUserId());
        System.out.println("saveUser.getUsername() = " + saveUser.getUsername());

    }
}