package backend.backend.userauth.api.entity.user;

import backend.backend.userauth.oauth2.entity.ProviderType;
import backend.backend.userauth.oauth2.entity.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @JsonIgnore
    @Id
    @Column(name = "user_seq")
    @GeneratedValue
    private Long userSeq;

    @Column(name = "user_id", length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private String userId;

    @Column(name = "username", length = 100)
    @NotNull
    @Size(max = 100)
    private String username;

//    @JsonIgnore
//    @Column(length = 128)
//    @NotNull
//    @Size(max = 128)
//    private String password;

    @Column(name = "EMAIL", length = 512, unique = true)
    @NotNull
    @Size(max = 512)
    private String email;

    @Column(name = "email_verified_yesno", length = 1)
    @NotNull
    @Size(min = 1, max = 1)
    private String emailVerifiedYn;

    @Column(name = "profile_image_url", length = 512)
    @Size(max = 512)
    private String profileImageUrl;

    @Column(name = "provider_type", length = 20)
    @Enumerated(STRING)
    @NotNull
    private ProviderType providerType;

    @Column(name = "role_type", length = 20)
    @Enumerated(STRING)
    @NotNull
    private RoleType roleType;

    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "modeified_at")
    @NotNull
    private LocalDateTime modifiedAt;


    @Builder
    public User(@NotNull @Size(max = 64) String userId,
                @NotNull @Size(max = 100) String username,
                @NotNull @Size(max = 512) String email,
                @NotNull @Size(max = 1) String emailVerifiedYn,
                @NotNull ProviderType providerType,
                @NotNull RoleType roleType,
                @NotNull LocalDateTime createdAt,
                @NotNull LocalDateTime modifiedAt
    ){
        this.userId = userId;
        this.username = username;
//        this.password = "NO_PASS";
        this.email = email != null? email: "NO_EMAIL";
        this.emailVerifiedYn = emailVerifiedYn;
        this.providerType = providerType;
        this.roleType = roleType;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
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
