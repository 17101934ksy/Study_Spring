//package capstonv1.capstonv1.api.member.presentation;
//
//import capstonv1.capstonv1.api.member.application.authrequest.AuthRequestModel;
//import capstonv1.capstonv1.api.member.domain.MemberRefreshToken;
//import capstonv1.capstonv1.api.member.infra.MemberRefreshTokenRepository;
//import capstonv1.capstonv1.common.ApiResponse;
//import capstonv1.capstonv1.config.properties.AppProperties;
//import capstonv1.capstonv1.oauth.domain.role.MemberPrincipal;
//import capstonv1.capstonv1.oauth.domain.role.RoleType;
//import capstonv1.capstonv1.oauth.domain.token.AuthToken;
//import capstonv1.capstonv1.oauth.domain.token.AuthTokenProvider;
//import capstonv1.capstonv1.utils.CookieUtil;
//import capstonv1.capstonv1.utils.HeaderUtil;
//import io.jsonwebtoken.Claims;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Date;
//
//@RestController
//@RequestMapping("/api/v1/auth")
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final AppProperties appProperties;
//    private final AuthTokenProvider tokenProvider;
//
//    private final AuthenticationManager authenticationManager;
//    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
//
//    private final static long THREE_DAYS_MSEC = 259200000;
//    private final static String REFRESH_TOKEN = "refresh_token";
////
////    @PostMapping("/login")
////    public ApiResponse login(
////            HttpServletRequest request,
////            HttpServletResponse response,
////            @RequestBody AuthRequestModel authReqModel
////    ) {
////        Authentication authentication = authenticationManager.authenticate();
////
////        String socialId = authReqModel.getId();
////        SecurityContextHolder.getContext().setAuthentication(authentication);
////
////        Date now = new Date();
////        AuthToken accessToken = tokenProvider.createAuthToken(
////                socialId,
////                ((MemberPrincipal) authentication.getPrincipal()).getRoleType().getCode(),
////                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
////        );
////
////        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
////        AuthToken refreshToken = tokenProvider.createAuthToken(
////                appProperties.getAuth().getTokenSecret(),
////                new Date(now.getTime() + refreshTokenExpiry)
////        );
////
////        // socialId refresh token 으로 DB 확인
////        MemberRefreshToken memberRefreshToken = memberRefreshTokenRepository.findBySocialId(socialId);
////        if (memberRefreshToken == null) {
////            // 없는 경우 새로 등록
////            memberRefreshToken = new MemberRefreshToken(socialId, refreshToken.getToken());
////            memberRefreshTokenRepository.saveAndFlush(memberRefreshToken);
////        } else {
////            // DB에 refresh 토큰 업데이트
////            memberRefreshToken.setRefreshToken(refreshToken.getToken());
////        }
////
////        int cookieMaxAge = (int) refreshTokenExpiry / 60;
////        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
////        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);
////
////        return ApiResponse.success("token", accessToken.getToken());
////    }
//
//    @GetMapping("/refresh")
//    public ApiResponse refreshToken (HttpServletRequest request, HttpServletResponse response) {
//        // access token 확인
//        String accessToken = HeaderUtil.getAccessToken(request);
//        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
//        if (!authToken.validate()) {
//            return ApiResponse.invalidAccessToken();
//        }
//
//        // expired access token 인지 확인
//        Claims claims = authToken.getExpiredTokenClaims();
//        if (claims == null) {
//            return ApiResponse.notExpiredTokenYet();
//        }
//
//        String socialId = claims.getSubject();
//        RoleType roleType = RoleType.of(claims.get("role", String.class));
//
//        // refresh token
//        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
//                .map(Cookie::getValue)
//                .orElse((null));
//        AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);
//
//        if (authRefreshToken.validate()) {
//            return ApiResponse.invalidRefreshToken();
//        }
//
//        // socialId refresh token 으로 DB 확인
//        MemberRefreshToken userRefreshToken = memberRefreshTokenRepository.findBySocialIdAndRefreshToken(socialId, refreshToken);
//        if (userRefreshToken == null) {
//            return ApiResponse.invalidRefreshToken();
//        }
//
//        Date now = new Date();
//        AuthToken newAccessToken = tokenProvider.createAuthToken(
//                socialId,
//                roleType.getCode(),
//                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
//        );
//
//        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();
//
//        // refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
//        if (validTime <= THREE_DAYS_MSEC) {
//            // refresh 토큰 설정
//            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
//
//            authRefreshToken = tokenProvider.createAuthToken(
//                    appProperties.getAuth().getTokenSecret(),
//                    new Date(now.getTime() + refreshTokenExpiry)
//            );
//
//            // DB에 refresh 토큰 업데이트
//            userRefreshToken.setRefreshToken(authRefreshToken.getToken());
//
//            int cookieMaxAge = (int) refreshTokenExpiry / 60;
//            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
//            CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
//        }
//
//        return ApiResponse.success("token", newAccessToken.getToken());
//    }
//}
