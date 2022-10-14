package backend.backend.channelmember.domain;

import backend.backend.Member.domain.Member;
import backend.backend.common.domain.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "channelmember")
public class ChannelMember extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "channelmember_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "channelplatform_id")
    private ChannelPlatform channelPlatform;

    @Enumerated(STRING)
    private ChannelRole role;
}
