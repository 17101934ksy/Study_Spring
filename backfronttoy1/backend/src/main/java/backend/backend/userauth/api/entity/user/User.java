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

    @Id
    @GeneratedValue
    private Long userSeq;

    @Column(length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private String userId;

    @Column(name = "username", length = 100)
    @NotNull
    @Size(max = 100)
    private String username;

    @Column(length = 512, unique = true)
    @NotNull
    @Size(max = 512)
    private String email;

    @Column(length = 1)
    @NotNull
    @Size(min = 1, max = 1)
    private String emailVerifiedYn;

    @Column(length = 512)
    @Size(max = 512)
    private String profileImageUrl;

    @Column(length = 20)
    @Enumerated(STRING)
    @NotNull
    private ProviderType providerType;

    @Column(length = 20)
    @Enumerated(STRING)
    @NotNull
    private RoleType roleType;

    @Column
    @NotNull
    private LocalDateTime createdAt;

    @Column
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
        this.email = email != null? email: "NO_EMAIL";
        this.emailVerifiedYn = emailVerifiedYn;
        this.profileImageUrl = null;
        this.providerType = providerType;
        this.roleType = roleType;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public void changeProfileImageUrl(@NotNull @Size(max = 512) String profileImageUrl){
        this.profileImageUrl = profileImageUrl;
        this.modifiedAt = LocalDateTime.now();
    }

    public void changeRoleType(@NotNull RoleType roleType){

        this.roleType = roleType;
        this.modifiedAt = LocalDateTime.now();
    }

    public void changeUsername(@NotNull @Size(max= 100) String username){

        this.username = username;
        this.modifiedAt = LocalDateTime.now();
    }

}
