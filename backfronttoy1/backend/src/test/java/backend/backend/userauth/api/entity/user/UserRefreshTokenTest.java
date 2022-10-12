package backend.backend.userauth.api.entity.user;

import backend.backend.userauth.oauth2.entity.ProviderType;
import backend.backend.userauth.oauth2.entity.RoleType;
import backend.backend.userauth.oauth2.entity.UserPrincipal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

import static backend.backend.userauth.oauth2.entity.ProviderType.GOOGLE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class UserRefreshTokenTest {

    @MockBean
    private UserPrincipal userPrincipal;

    @MockBean User user;

    @Test
    public void userPrincipal_생성자() throws Exception {

        //given
        when(user.getUserId()).thenReturn("test123");
        when(user.getProviderType()).thenReturn(GOOGLE);

        //when
        UserPrincipal createdUserPrincipal = UserPrincipal.create(user);

        //then
        Assertions.assertThat(createdUserPrincipal.getUserId()).isEqualTo("test123");
    }

}