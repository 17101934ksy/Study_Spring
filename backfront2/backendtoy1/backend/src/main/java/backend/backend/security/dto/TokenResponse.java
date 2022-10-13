package backend.backend.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenResponse {

    private String tokenType = "Bearer";
    private String accessToken;
    private String refreshToken;

    public static TokenResponse from (TokenResponseForDto tokenResponseForDto) {
        return new TokenResponse(tokenResponseForDto.getAccessToken(), tokenResponseForDto.getRefreshToken());
    }

    public static TokenResponse of(String accessToken,String refreshToken){
        return new TokenResponse(accessToken,refreshToken);
    }

    private TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
