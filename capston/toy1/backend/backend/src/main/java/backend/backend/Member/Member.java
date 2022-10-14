package backend.backend.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

@Getter
@NoArgsConstructor
@Entity
@Table
public class Member implements Serializable {

    @Id
    @Column(name = "member_id")
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String principal;

    @Enumerated(STRING)
    private SocialType socialType;

    @Builder
    public Member(String name, String email, String principal, SocialType socialType){
        this.name = name;
        this.email = email;
        this.principal = principal;
        this.socialType = socialType;
    }
}
