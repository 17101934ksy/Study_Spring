package backend.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClientRegistrationService {

    final Environment env;

    public ClientRegistrationService(Environment env) {
        this.env = env;
    }

    public Map<String, Object> clientRegistrationService(String registrationId) {
        Map<String, Object> clientService = new HashMap<>();

        switch (registrationId.toLowerCase()) {

            case "google":
                System.out.println("env = " + env.getProperty("spring.security.oaut2.client.registration.google.client-id)"));
                clientService.put("clientId", env.getProperty("spring.security.oaut2.client.registration.google.client-id)"));
                break;

            default:
                System.out.println("default " + env.getProperty("spring.security.oaut2.client.registration.google.client-secret"));
        }

        return clientService;
    }

}
