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

    public static OAuth2UserImpl of(Member member, Map<String, Object> attributes) {
        OAuth2UserImpl oAuth2User = new OAuth2UserImpl(member);
        oAuth2User.attributes = attributes;
        return oAuth2User;
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
