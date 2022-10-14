package backend.backend.Member.domain;

import backend.backend.channelmember.domain.ChannelMember;
import backend.backend.channelmember.domain.ChannelPlatform;
import backend.backend.channelmember.domain.MemberShip;
import backend.backend.common.domain.BaseEntity;
import backend.backend.security.domain.AuthProvider;
import backend.backend.security.domain.Role;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.EnumType.STRING;

@NoArgsConstructor
@Getter
@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Enumerated(STRING)
    @Column(nullable = false)
    private AuthProvider authProvider;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "host")
    private List<ChannelPlatform> channelPlatforms = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ChannelMember> channelMembers = new ArrayList<>();

    private int managementFee;

    @Enumerated(STRING)
    private MemberShip membership;

    private int nextMonthExpectedDiscount;

    private int totalFee;

    private LocalDateTime paymentDueDate;

    private boolean paymentComplementYN;

    private Date overDueDate;

    private LocalDateTime paymentCompletedAt;

    @Builder
    public Member(Long id, String email, String name, AuthProvider authProvider, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.authProvider = authProvider;
        this.role = role;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }

    public void updateNextMonthExpectedDiscount(int nextMonthExpectedDiscount){
        this.nextMonthExpectedDiscount = nextMonthExpectedDiscount;
    }
}
