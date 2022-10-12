package backend.backend.userauth.api.entity.user;

import backend.backend.userauth.api.repository.user.UserRepository;
import backend.backend.userauth.oauth2.entity.ProviderType;
import backend.backend.userauth.oauth2.entity.RoleType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static backend.backend.userauth.oauth2.entity.ProviderType.GOOGLE;
import static backend.backend.userauth.oauth2.entity.ProviderType.KAKAKO;
import static backend.backend.userauth.oauth2.entity.RoleType.ADMIN;
import static backend.backend.userauth.oauth2.entity.RoleType.USER;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserTest {

    @Autowired
    static User user;

    @Autowired
    static UserRepository userRepository;

    @Test
    public void 유저_생성자_생성() throws Exception {
        //given

        user = User.builder()
                .userId("12341")
                .username("1000")
                .email("ttt@naver.com")
                .emailVerifiedYn("Y")
                .providerType(GOOGLE)
                .roleType(USER)
                .createdAt(now())
                .modifiedAt(now())
                .build();
        //when

        //then

        assertThat(user.getEmail()).isEqualTo("ttt@naver.com");
    }

    @Test
    public void 유저_프로필_이미지_변경() throws Exception {
        //given
        user = User.builder()
                .userId("12342")
                .username("1000")
                .email("ttt@naver.com")
                .emailVerifiedYn("Y")
                .providerType(GOOGLE)
                .roleType(USER)
                .createdAt(now())
                .modifiedAt(now())
                .build();

        LocalDateTime now = LocalDateTime.now();

        //when
        user.changeProfileImageUrl("//userChangeImage");

        //then
        assertThat(user.getProfileImageUrl()).isEqualTo("//userChangeImage");

        assertThat(user.getModifiedAt()).isAfter(now);
    }
    
    @Test
    public void 유저_롤_변경() throws Exception {
        //given
        user = User.builder()
                .userId("123425")
                .username("1000")
                .email("ttt@naver.com")
                .emailVerifiedYn("Y")
                .providerType(GOOGLE)
                .roleType(USER)
                .createdAt(now())
                .modifiedAt(now())
                .build();

        LocalDateTime now = LocalDateTime.now();

        //when
        user.changeRoleType(ADMIN);

        //then
        assertThat(user.getRoleType()).isEqualTo(ADMIN);

        assertThat(user.getModifiedAt()).isAfter(now);
    }

    @Test
    public void 유저_유저네임_변경() throws Exception {
        //given
        user = new User();
        user = User.builder()
                .userId("123425")
                .username("1000")
                .email("ttt@naver.com")
                .emailVerifiedYn("Y")
                .providerType(GOOGLE)
                .roleType(USER)
                .createdAt(now())
                .modifiedAt(now())
                .build();

        LocalDateTime now = LocalDateTime.now();

        //when
        user.changeUsername("ksy");

        //then
        assertThat(user.getUsername()).isEqualTo("ksy");

        assertThat(user.getModifiedAt()).isAfter(now);
    }
}