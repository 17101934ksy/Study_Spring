package capstonv1.capstonv1.api.member.domain;

import capstonv1.capstonv1.common.BaseEntity;
import capstonv1.capstonv1.oauth.domain.role.ProviderType;
import capstonv1.capstonv1.oauth.domain.role.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String socialId;

    private String username;

    private String email;

    private String profileImageUrl;

    @Enumerated(STRING)
    private ProviderType providerType;

    @Enumerated(STRING)
    private RoleType roleType;

    @Builder
    public Member(String socialId, String username, String email, ProviderType providerType, RoleType roleType) {
        this.socialId = socialId;
        this.username = username;
        this.email = email;
        this.providerType = providerType;
        this.roleType = roleType;
    }

    public void updateUsername(String username) {
        this.username = username;
    }
}
