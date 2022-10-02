package kakaoToy1.kakaoToy1.domain;

import kakaoToy1.kakaoToy1.domain.member.Member;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @ToString
public class TestSave {

    @Id @GeneratedValue
    @Column(name = "save_id")
    private Long id;

    private int testNumber;
    private String answer;

    @Embedded CreateAndModifyDate createAndModifyDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "test_space_id")
    private TestSpace testSpace;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "test_id")
    private TestProblem testProblem;

    public TestSave() {}

    public TestSave(int testNumber, String answer, TestProblem testProblem) {
        this.testNumber = testNumber;
        this.answer = answer;
        this.createAndModifyDate = new CreateAndModifyDate();
        this.testProblem = testProblem;
    }

    //필드값 주입 메서드

    public void changeAnswer(String answer){
        this.answer = answer;
        this.createAndModifyDate.changeModifiedDate();
    }

}
