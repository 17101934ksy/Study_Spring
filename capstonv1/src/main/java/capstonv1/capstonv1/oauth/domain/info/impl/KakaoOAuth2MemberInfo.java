package capstonv1.capstonv1.oauth.domain.info.impl;

import capstonv1.capstonv1.oauth.domain.info.OAuth2MemberInfo;

import java.util.Map;

public class KakaoOAuth2MemberInfo extends OAuth2MemberInfo {

    public KakaoOAuth2MemberInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        if (properties == null) {
            return null;
        }

        return (String) properties.get("nickname");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("account_email");
    }

}
