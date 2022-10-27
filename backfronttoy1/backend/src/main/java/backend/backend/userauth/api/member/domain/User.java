package backend.backend.userauth.api.member.domain;

import backend.backend.userauth.oauth2.domain.ProviderType;
import backend.backend.userauth.oauth2.domain.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Id @GeneratedValue
    private Long userSeq;

    private String userId;

    private String username;

    private String email;

    private String emailVerifiedYn;

    private String profileImageUrl;
    private ProviderType providerType;
    private RoleType roleType;

    @Builder
    public User(String userId,
                String username,
                String email,
                String emailVerifiedYn,
                ProviderType providerType,
                RoleType roleType
    ){
        this.userId = userId;
        this.username = username;
        this.email = email != null? email: "NO_EMAIL";
        this.emailVerifiedYn = emailVerifiedYn;
        this.profileImageUrl = null;
        this.providerType = providerType;
        this.roleType = roleType;
    }

    public void changeProfileImageUrl(@NotNull @Size(max = 512) String profileImageUrl){
        this.profileImageUrl = profileImageUrl;
    }

    public void changeRoleType(@NotNull RoleType roleType){
        this.roleType = roleType;
    }

    public void changeUsername(@NotNull @Size(max= 100) String username){
        this.username = username;
    }

}
