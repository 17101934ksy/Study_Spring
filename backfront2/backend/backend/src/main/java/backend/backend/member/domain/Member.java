package backend.backend.member.domain;


import backend.backend.auth.enumerate.RoleType;
import backend.backend.common.domain.BaseEntity;
import backend.backend.member.enumerate.MemberProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@RequiredArgsConstructor
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String socialId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(STRING)
    @Column(nullable = false)
    private MemberProvider memberProvider;

    @Enumerated(STRING)
    @Column(nullable = false)
    private RoleType roleType;

    @Builder
    private Member (String socialId, String name, String email, MemberProvider memberProvider, RoleType roleType){
        this.socialId = socialId;
        this.name = name;
        this.email = email;
        this.memberProvider = memberProvider;
        this.roleType = roleType;
    }

    public void updateName(String name) {
        this.name = name;
    }


}
