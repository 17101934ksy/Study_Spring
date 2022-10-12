package backend.backend.userauth.config.security;

import backend.backend.userauth.api.repository.user.UserRefreshTokenRepository;
import backend.backend.userauth.config.properties.AppProperties;
import backend.backend.userauth.config.properties.CorsProperties;
import backend.backend.userauth.oauth2.entity.RoleType;
import backend.backend.userauth.oauth2.exception.RestAuthenticationEntryPoint;
import backend.backend.userauth.oauth2.filter.TokenAuthenticationFilter;
import backend.backend.userauth.oauth2.handler.OAuth2AuthenticationFailureHandler;
import backend.backend.userauth.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import backend.backend.userauth.oauth2.handler.TokenAccessDeniedHandler;
import backend.backend.userauth.oauth2.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import backend.backend.userauth.oauth2.service.CustomOAuth2UserService;
import backend.backend.userauth.oauth2.service.CustomUserDetailsService;
import backend.backend.userauth.oauth2.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.Filter;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsProperties corsProperties;
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    /**
     * UserDetailsService 설정
     */

    @Bean
    protected SecurityFilterChain filterChain(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder());

        return (SecurityFilterChain) auth.build();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .accessDeniedHandler(tokenAccessDeniedHandler)
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/api/**").hasAnyAuthority(RoleType.USER.getCode())
                .antMatchers("/api/**/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
                .authorizationRequestRepository((AuthorizationRequestRepository<OAuth2AuthorizationRequest>) oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                .redirectionEndpoint()
                .baseUri("/*/oauth2/code/*")
                .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler((AuthenticationSuccessHandler) oAuth2AuthenticationSuccessHandler())
                .failureHandler((AuthenticationFailureHandler) oAuth2AuthenticationFailureHandler())
                .and()
                .logout()
                .logoutSuccessUrl("http://localhost:3000")
                .and()
                .csrf()
                .ignoringAntMatchers("/h2-console/**")
                .and()
                .headers().frameOptions().disable();

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     *  auth 매니저 설정
     */



    /**
     * security 설정 시, 사용할 인코더
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 토큰 필터 설정
     */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    /**
     * 쿠키 기반 인가 Repository
     * 인가 응답을 연계 하고 검증할 때 사용
     */
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    /**
     * Oauth 인증 성공 핸들러
     */
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                tokenProvider,
                appProperties,
                userRefreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository()
        );
    }

    /**
     * Oauth 인증 실페 핸들러
     */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
    }


    /**
     * Cors 설정
     */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        corsConfiguration.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        corsConfiguration.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(corsConfiguration.getMaxAge());

        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }


}
