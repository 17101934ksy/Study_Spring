package backend.backend.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = ClientRegistrationProperties.class)
class ClientRegistrationPropertiesTest {

    @Autowired
    private ClientRegistrationProperties clientRegistrationProperties;

    @Test
    public void yml프로퍼티() throws Exception {
        //given

        System.out.println("clientRegistrationProperties.getGoogle() = " + clientRegistrationProperties.getClientId());

        //when

        //then

    }

}