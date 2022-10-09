package backend.backend.userauth.oauth2.info.impl;

import backend.backend.userauth.oauth2.info.OAuth2UserInfo;

import java.util.Map;

public class GoogleAuth2UserInfo extends OAuth2UserInfo {

    public GoogleAuth2UserInfo(Map<String, Object> attributes){
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}
