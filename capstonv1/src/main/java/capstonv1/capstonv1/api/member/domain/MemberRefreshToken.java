package capstonv1.capstonv1.api.member.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MemberRefreshToken {

    @Id @GeneratedValue
    @Column(name = "refresh_token_id")
    private Long id;

    private String socialId;

    private String refreshToken;

    @Builder
    public MemberRefreshToken(String socialId, String refreshToken) {
        this.socialId = socialId;
        this.refreshToken = refreshToken;
    }
}
