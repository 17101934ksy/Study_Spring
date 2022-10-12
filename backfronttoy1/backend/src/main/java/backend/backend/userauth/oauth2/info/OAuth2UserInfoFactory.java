package backend.backend.userauth.oauth2.info;

import backend.backend.userauth.oauth2.entity.ProviderType;
import backend.backend.userauth.oauth2.info.impl.FacebookOAuth2UserInfo;
import backend.backend.userauth.oauth2.info.impl.GoogleAuth2UserInfo;
import backend.backend.userauth.oauth2.info.impl.KakaoOAuth2UserInfo;
import backend.backend.userauth.oauth2.info.impl.NaverOAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {

        switch (providerType){

            case GOOGLE:
                log.info("Google login try - OAuth2UserInfoFactory");
                return new GoogleAuth2UserInfo(attributes);

            case FACEBOOK:
                log.info("Facebook login try - OAuth2UserInfoFactory");
                return new FacebookOAuth2UserInfo(attributes);

            case NAVER:
                log.info("Naver login try - OAuth2UserInfoFactory");
                return new NaverOAuth2UserInfo(attributes);

            case KAKAKO:
                log.info("Kakao login try - OAuth2UserInfoFactory");
                return new KakaoOAuth2UserInfo(attributes);

            default:
                log.info("제공되지 않는 로그인 방법");
                throw new IllegalArgumentException("제공되지 않는 로그인 방법");
        }
    }
}
