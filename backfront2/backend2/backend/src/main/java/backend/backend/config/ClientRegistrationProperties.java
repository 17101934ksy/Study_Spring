package backend.backend.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@RequiredArgsConstructor
@PropertySource(value = "classpath:application-oauth.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.google")
@Getter
public class ClientRegistrationProperties {

    private final String clientId;

}
