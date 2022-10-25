package capstonv1.capstonv1.oauth.domain.info.impl;

import capstonv1.capstonv1.oauth.domain.info.OAuth2MemberInfo;

import java.util.Map;

public class GoogleOAuth2MemberInfo extends OAuth2MemberInfo {

    public GoogleOAuth2MemberInfo(Map<String, Object> attributes) {
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
