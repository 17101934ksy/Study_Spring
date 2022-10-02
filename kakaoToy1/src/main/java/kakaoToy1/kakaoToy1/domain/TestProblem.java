package kakaoToy1.kakaoToy1.domain;

import kakaoToy1.kakaoToy1.domain.member.Member;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @ToString
public class TestProblem {

    @Id @GeneratedValue
    @Column(name = "test_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private TestSubject testSubject;

    private int testNumber;

    @Embedded CreateAndModifyDate createAndModifyDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //== 필드값 주입 메서드
    public void changeTestSubject(TestSubject testSubject){
        this.testSubject = testSubject;
        this.createAndModifyDate.changeModifiedDate();
    }

    public void changeTestNumber(int testNumber){
        this.testNumber = testNumber;
        this.createAndModifyDate.changeModifiedDate();
    }
}
