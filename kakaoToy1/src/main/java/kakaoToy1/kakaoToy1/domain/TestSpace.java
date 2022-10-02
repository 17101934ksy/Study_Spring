package kakaoToy1.kakaoToy1.domain;

import kakaoToy1.kakaoToy1.domain.member.Member;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @ToString
public class TestSpace {

    @Id @GeneratedValue
    @Column(name = "test_space_id")
    private Long id;
    private String name;

    @Embedded CreateAndModifyDate createAndModifyDate;

    private int maxMember;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "testSpace")
    private List<ChattingLog> chattingLogs = new ArrayList<>();

    @OneToMany(mappedBy = "testSpace")
    private List<TestSave> testSaves = new ArrayList<>();

    public TestSpace() {}

    public TestSpace(String name, int maxMember) {
        this.createAndModifyDate.setCreatedDate();
        this.name = name;
        this.maxMember = maxMember;
    }

    //== 필드값 주입 메서드
    public void changeMaxMember(int maxMember){
        this.maxMember = maxMember;
        this.createAndModifyDate.changeModifiedDate();
    }

    public void changeName(String name){
        this.name = name;
        this.createAndModifyDate.changeModifiedDate();
    }

}

