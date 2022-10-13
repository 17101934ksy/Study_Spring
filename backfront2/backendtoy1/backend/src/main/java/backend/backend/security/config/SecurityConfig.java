package backend.backend.security.config;

import backend.backend.security.domain.Role;
import backend.backend.security.dto.RestAuthenticationEntryPoint;
import backend.backend.security.filter.HomeAccessFilter;
import backend.backend.security.filter.RequestResponseLoggingFilter;
import backend.backend.security.handler.OAuth2AuthenticationFailureHandler;
import backend.backend.security.handler.OAuth2AuthenticationSuccessHandler;
import backend.backend.security.handler.TokenAccessDeniedHandler;
import backend.backend.security.service.CustomOauth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;

import static backend.backend.security.domain.Role.ADMIN;
import static backend.backend.security.domain.Role.USER;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final CustomOauth2Service customOauth2Service;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .httpBasic().disable()
//                .formLogin().disable()
//                .csrf().disable()
//                .cors()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(STATELESS)
//                .and()
//                .oauth2Login()
//                .userInfoEndpoint()
//                .userService(customOauth2Service)
//                .and()
//                .successHandler(oAuth2AuthenticationSuccessHandler)
//                .failureHandler(oAuth2AuthenticationFailureHandler)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/login", "/refresh").permitAll()
//                .anyRequest().hasAnyRole(USER.getKey(), ADMIN.getKey())
//                ;
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
                .antMatchers("/api/**").hasAnyAuthority(Role.USER.getKey())
                .antMatchers("/api/**/admin/**").hasAnyAuthority(Role.ADMIN.getKey())
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
//                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                .redirectionEndpoint()
                .baseUri("/*/oauth2/code/*")
                .and()
                .userInfoEndpoint()
                .userService(customOauth2Service)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web ->
                web.ignoring().antMatchers("/images/**", "/js/**", "/webjars"));}


    // 필터링
    @Bean
    public FilterRegistrationBean<HomeAccessFilter> homeAccessFilter(){
        FilterRegistrationBean<HomeAccessFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new HomeAccessFilter());
        registrationBean.addUrlPatterns("*");
        registrationBean.setOrder(2);

        return registrationBean;
    }

}
