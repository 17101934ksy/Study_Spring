package backend.backend.userauth.oauth2.info;

import backend.backend.userauth.oauth2.entity.ProviderType;
import backend.backend.userauth.oauth2.info.impl.FacebookOAuth2UserInfo;
import backend.backend.userauth.oauth2.info.impl.GoogleAuth2UserInfo;
import backend.backend.userauth.oauth2.info.impl.KakaoOAuth2UserInfo;
import backend.backend.userauth.oauth2.info.impl.NaverOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {

        switch (providerType){

            case GOOGLE: return new GoogleAuth2UserInfo(attributes);
            case FACEBOOK: return new FacebookOAuth2UserInfo(attributes);
            case NAVER: return new NaverOAuth2UserInfo(attributes);
            case KAKAKO: return new KakaoOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("제공되지 않는 로그인 방법");
        }
    }
}
