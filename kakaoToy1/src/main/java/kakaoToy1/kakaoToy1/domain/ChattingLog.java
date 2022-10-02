package kakaoToy1.kakaoToy1.domain;

import kakaoToy1.kakaoToy1.domain.member.Member;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter @ToString
public class ChattingLog {

    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime deleteDate;
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "test_space_id")
    private TestSpace testSpace;


    public ChattingLog() {}

    public ChattingLog(String content, Member member, TestSpace testSpace) {
        this.createdDate = LocalDateTime.now();
        this.content = content;
        this.member = member;
        this.testSpace = testSpace;
    }

    //== 필드값 주입 메서드
    public void changeDeleteDate(){
        this.deleteDate = LocalDateTime.now();
    }

    //== 얀관관계 편의 메서드



}
