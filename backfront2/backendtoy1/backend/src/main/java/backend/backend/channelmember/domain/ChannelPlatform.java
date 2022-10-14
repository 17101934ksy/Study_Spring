package backend.backend.channelmember.domain;
import backend.backend.Member.domain.Member;
import backend.backend.common.domain.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@RequiredArgsConstructor
@ToString
@Table(name = "channelplatform")
public class ChannelPlatform extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "channelplatform_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member host;

    @OneToMany(mappedBy = "channelPlatform")
    private List<ChannelMember> channelMembers = new ArrayList<>();

}
