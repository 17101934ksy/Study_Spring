package backend.backend.security.dto;

import backend.backend.Member.domain.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class OAuth2UserImpl implements OAuth2User {

    private Member member;
    private Map<String, Object> attributes;

    private OAuth2UserImpl(Member member) {
        this.member = member;
    }

    public static OAuth2UserImpl from(Member member) {
        return new OAuth2UserImpl(member);
    }

    public static OAuth2UserImpl of(Member member, Map<String, Object> attributes) {
        OAuth2UserImpl oAuth2User = OAuth2UserImpl.from(member);
        oAuth2User.attributes = attributes;
        return oAuth2User;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(member.getRoleKey()));
    }

    @Override
    public String getName() {
        return member.getName();
    }
}
