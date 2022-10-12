package backend.backend.userauth.oauth2.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.management.relation.Role;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class RoleTypeTest {

    @Test
    public void of_권한부여() throws Exception {
        //given
        RoleType roleUser = RoleType.of("ROLE_USER");
        RoleType roleAdmin = RoleType.of("ROLE_ADMIN");

        RoleType roleGuest = RoleType.of("GUEST");
        RoleType roleGuest1 = RoleType.of("ADMIN");
        RoleType roleEtc = RoleType.of("etc");

        //when

        System.out.println("roleUser.getCode() = " + roleUser.getCode());

        //then
        assertThat(roleUser.getCode()).isEqualTo("ROLE_USER");
        assertThat(roleAdmin.getCode()).isEqualTo("ROLE_ADMIN");
        assertThat(roleGuest.getCode()).isEqualTo("GUEST");
        assertThat(roleGuest1.getCode()).isEqualTo("GUEST");
        assertThat(roleEtc.getCode()).isEqualTo("GUEST");


    }
}