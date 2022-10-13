package backend.backend.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenResponseForDto {

    private String tokenType = "Bearer";
    private String accessToken;
    private String refreshToken;

    private TokenResponseForDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static TokenResponseForDto of(String accessToken, String refreshToken){
        return new TokenResponseForDto(accessToken,refreshToken);
    }

}
