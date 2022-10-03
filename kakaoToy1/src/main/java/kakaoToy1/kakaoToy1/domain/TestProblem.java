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
public class TestProblem {

    @Id @GeneratedValue
    @Column(name = "test_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private TestSubject testSubject;

    private Long subjectNumber;
    private Long classNumber;

    private LocalDateTime testDate;

    private int testNumber;

    private String problem;

    @Embedded CreateAndModifyDate createAndModifyDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "testProblem")
    private List<TestSave> testSaves = new ArrayList<>();

    public TestProblem() {}
    public TestProblem(TestSubject testSubject, Long subjectNumber, Long classNumber, LocalDateTime testDate, int testNumber, String problem, Member member) {
        this.testSubject = testSubject;
        this.subjectNumber = subjectNumber;
        this.classNumber = classNumber;
        this.testDate = testDate;
        this.testNumber = testNumber;
        this.problem = problem;
        this.createAndModifyDate = new CreateAndModifyDate();
        this.member = member;
    }

    //== 필드값 주입 메서드
    public void changeSubjectNumber(Long subjectNumber){
        this.subjectNumber = subjectNumber;
        this.createAndModifyDate.changeModifiedDate();
    }

    public void changeClassNumber(Long classNumber){
        this.classNumber = classNumber;
        this.createAndModifyDate.changeModifiedDate();
    }

    public void changeTestDate(LocalDateTime testDate){
        this.testDate = testDate;
        this.createAndModifyDate.changeModifiedDate();
    }

    public void changeTestSubject(TestSubject testSubject){
        this.testSubject = testSubject;
        this.createAndModifyDate.changeModifiedDate();
    }

    public void changeTestNumber(int testNumber){
        this.testNumber = testNumber;
        this.createAndModifyDate.changeModifiedDate();
    }

    public void changeProblem(String problem){
        this.problem = problem;
        this.createAndModifyDate.changeModifiedDate();
    }

    //== 연관관계 주입 메서드
    public void addTestSaves(TestSave testSave){
        testSaves.add(testSave);
    }
}
