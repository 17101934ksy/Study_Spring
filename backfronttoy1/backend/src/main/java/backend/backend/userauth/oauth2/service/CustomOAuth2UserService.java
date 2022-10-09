package backend.backend.userauth.oauth2.service;

import backend.backend.userauth.api.entity.user.User;
import backend.backend.userauth.oauth2.entity.ProviderType;
import backend.backend.userauth.oauth2.entity.RoleType;
import backend.backend.userauth.oauth2.entity.UserPrincipal;
import backend.backend.userauth.oauth2.exception.OAuthProviderMissMatchException;
import backend.backend.userauth.oauth2.info.OAuth2UserInfo;
import backend.backend.userauth.oauth2.info.OAuth2UserInfoFactory;
import backend.backend.userauth.oauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

import static backend.backend.userauth.oauth2.entity.RoleType.USER;
import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }

    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        User savedUser = userRepository.findByUserId(userInfo.getId());

        if (savedUser != null){
            if (providerType != savedUser.getProviderType()) {
                throw new OAuthProviderMissMatchException(
                        "Looks like you're signed up with" + providerType +
                        "account. Please use your " + savedUser.getProviderType() + "account to login"
                );
            }
            updatedUser(savedUser, userInfo);
        } else {
            savedUser = createUser(userInfo, providerType);
        }

        return UserPrincipal.create(savedUser, user.getAttributes());
    }

    private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        LocalDateTime now = now();
        User user = User.builder()
                .userId(userInfo.getId())
                .username(userInfo.getName())
                .email(userInfo.getEmail())
                .emailVerifiedYn("Y")
                .providerType(providerType)
                .roleType(USER)
                .createdAt(now)
                .modifiedAt(now)
                .build();

        return userRepository.saveAndFlush(user);
    }

    private User updatedUser(User savedUser, OAuth2UserInfo userInfo) {
        if (userInfo.getName() != null && !savedUser.getUsername().equals(userInfo.getName())){
            savedUser.changeUsername(userInfo.getName());
        }

        return savedUser;
    }
}
