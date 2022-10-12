package backend.backend.userauth.api.repository.user;

import backend.backend.userauth.api.entity.user.UserRefreshToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class UserRefreshTokenRepositoryTest {

    @SpyBean
    private UserRefreshTokenRepository userRefreshTokenRepository;

    static UserRefreshToken refreshToken;

    @Test
    public void 토큰테스트() throws Exception {
        //given
        refreshToken = new UserRefreshToken("test1234", "refreshToken");
        userRefreshTokenRepository.save(refreshToken);

        //when

        //then

    }
}