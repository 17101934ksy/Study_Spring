package backend.backend.userauth.api.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "uset_refresh_token")
public class UserRefreshToken {

    @JsonIgnore
    @Id
    @GeneratedValue
    private Long refreshTokenSeq;

    @Column(length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private String userId;

    @Column(length = 256)
    @NotNull
    @Size(max= 256)
    private String refreshToken;

    public UserRefreshToken(@NotNull @Size(max = 64) String userId, @NotNull @Size(max = 256) String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    public void changeRefreshToken(@NotNull @Size(max = 256) String refreshToken){
        this.refreshToken = refreshToken;
    }

}
