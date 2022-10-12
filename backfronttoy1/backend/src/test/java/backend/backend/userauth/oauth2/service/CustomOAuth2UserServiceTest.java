package backend.backend.userauth.oauth2.service;

import backend.backend.userauth.api.entity.user.User;
import backend.backend.userauth.api.repository.user.UserRepository;
import backend.backend.userauth.oauth2.entity.ProviderType;
import backend.backend.userauth.oauth2.entity.RoleType;
import backend.backend.userauth.oauth2.entity.UserPrincipal;
import backend.backend.userauth.oauth2.exception.OAuthProviderMissMatchException;
import backend.backend.userauth.oauth2.info.OAuth2UserInfo;
import backend.backend.userauth.oauth2.info.OAuth2UserInfoFactory;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static backend.backend.userauth.oauth2.entity.ProviderType.GOOGLE;
import static backend.backend.userauth.oauth2.entity.RoleType.ADMIN;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CustomOAuth2UserServiceTest {

    @MockBean
    CustomOAuth2UserService customOAuth2UserService;

    User user;
    User userUserInfoNotGetName;

    @MockBean
    OAuth2UserInfo userInfo;

    @SpyBean
    UserRepository userRepository;

    @MockBean
    OAuth2UserRequest oAuth2UserRequest;

    @MockBean
    OAuth2User oAuth2User;

    @MockBean
    ClientRegistration clientRegistration;

    @Test
    public void updatedUser_업데이트성공() throws Exception {

        //given
        user = createdUser();
        when(userInfo.getName()).thenReturn("testNameUserInfo");

        //when
        try{
            Method updateUser = copyUpdateUser();
            updateUser.setAccessible(true);
            user = (User) updateUser.invoke(customOAuth2UserService, user, userInfo);
        } catch (Exception e){
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        
        //then
        assertThat(userInfo.getName()).isEqualTo("testNameUserInfo");
        assertThat(user.getUsername()).isEqualTo(userInfo.getName());
    }

    @Test
    public void updatedUser_userInfo의_getName이_null인_경우() throws Exception {

        //given
        user = createdUser();

        //when
        try{
            Method updateUser = copyUpdateUser();
            updateUser.setAccessible(true);
            user = (User) updateUser.invoke(customOAuth2UserService, user, userInfo);
            
        } catch (Exception e){
            System.out.println("e.getMessage() = " + e.getMessage());
        }

        //then
        assertThat(user.getUsername()).isEqualTo("testName");
    }

    @Test
    public void createUser() throws Exception {
        //given
        when(userInfo.getId()).thenReturn("seyunId");
        when(userInfo.getName()).thenReturn("seyunName");
        when(userInfo.getEmail()).thenReturn("seyun@seyun.com");

        //when
        User userByCreatedUser = null;

        try {
            Method createdUser = copyCreateUser();
            createdUser.setAccessible(true);
            userByCreatedUser = (User) createdUser.invoke(customOAuth2UserService, userInfo, GOOGLE);

        } catch (Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }

        //then
        assertThat(userByCreatedUser.getUserId()).isEqualTo(userInfo.getId());
        assertThat(userByCreatedUser.getEmail()).isEqualTo(userInfo.getEmail());
        assertThat(userByCreatedUser.getProfileImageUrl()).isNull();
    }


    @Test
    public void procee_예외() throws Exception {
        //given
        Method process = copyProcess();
        process.setAccessible(true);

        try{
//            when(clientRegistration.getRegistrationId()).thenReturn("google");
            given(oAuth2UserRequest.getClientRegistration().getRegistrationId()).willReturn("google");
        } catch (Exception e){
            System.out.println("e.getMessage() = " + e.getMessage());
        }
//        when(oAuth2UserRequest.getClientRegistration()).thenReturn(clientRegistration);
//        when(oAuth2UserRequest.getClientRegistration().getRegistrationId()).thenReturn("google");
//        when(oAuth2User.getAttributes().get("sub")).thenReturn("testId1234");
//        when(oAuth2User.getAttributes().get("name")).thenReturn("ksy");
//        when(oAuth2User.getAttributes().get("email")).thenReturn("ttt@google.com");

//        OAuth2User oAuth2UserByProcess = process.invoke(customOAuth2UserService, )



        //when

        //then

    }



    public Method copyProcess() throws NoSuchMethodException {
        customOAuth2UserService = new CustomOAuth2UserService(userRepository);
        Method method = customOAuth2UserService.getClass().getDeclaredMethod("process", OAuth2UserRequest.class, OAuth2User.class);

        return method;
    }

    public Method copyUpdateUser() throws NoSuchMethodException {
        customOAuth2UserService = new CustomOAuth2UserService(userRepository);
        Method method = customOAuth2UserService.getClass().getDeclaredMethod("updatedUser", User.class, OAuth2UserInfo.class);

        return method;
    }

    public Method copyCreateUser() throws NoSuchMethodException {
        customOAuth2UserService = new CustomOAuth2UserService(userRepository);
        Method method = customOAuth2UserService.getClass().getDeclaredMethod("createUser", OAuth2UserInfo.class, ProviderType.class);

        return method;
    }

    private User createdUser(){
        User user = User.builder()
                .userId("test1234").username("testName").modifiedAt(now()).createdAt(now())
                .providerType(GOOGLE).roleType(ADMIN).emailVerifiedYn("Y").email("test@test.com")
                .build();
        return user;
    }
}