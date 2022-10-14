package backend.backend.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class LoginController {


    @GetMapping("/")
    public HashMap<String, Object> index(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                                         @AuthenticationPrincipal OAuth2User oauth2User) {
        HashMap<String, Object> results = new HashMap<>();
        results.put("username", oauth2User.getName());
        results.put("attributes", oauth2User.getAttributes());
        results.put("authorities", oauth2User.getAuthorities());
        results.put("clientScopes", authorizedClient.getClientRegistration().getScopes());
        results.put("clientName", authorizedClient.getClientRegistration().getClientName());
        return results;
    }
}

