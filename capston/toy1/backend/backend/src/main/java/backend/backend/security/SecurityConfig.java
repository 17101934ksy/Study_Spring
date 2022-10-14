package backend.backend.security;

import backend.backend.security.handler.OAuth2SuccessHandler;
import backend.backend.security.service.CustomOAuth2UserService;
import backend.backend.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    @Bean
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/oauth2/**", "/login/**", "/css/**",
                        "/images/**", "/js/**", "/console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .oauth2Login().defaultSuccessUrl("/loginSucess").failureUrl("/loginFailure")
                .and()
                .headers().frameOptions().disable()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/").deleteCookies("JSESSION").invalidateHttpSession(true);
    }

}
