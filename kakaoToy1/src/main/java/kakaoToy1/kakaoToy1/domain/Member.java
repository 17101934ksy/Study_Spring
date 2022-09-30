package kakaoToy1.kakaoToy1.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
public class Member {

    @Id @GeneratedValue
    @Column(name = "memberId")
    private Long id;

    @Enumerated(EnumType.STRING)
    private MemberLoginWay memberLoginWay;

    private Long userId;

    private String job;

    private Long studentId;

    private Long professorId;

    private String username;

    private String university;

    private String department;

    private String email;

    @Enumerated(EnumType.STRING)
    private MemberAuthStatus status;

    public Member() {}

    public Member(MemberLoginWay memberLoginWay, Long userId) {
        this.memberLoginWay = memberLoginWay;
        this.userId = userId;
        this.status = MemberAuthStatus.GUEST;
    }

    //== 필드값 주입 메서드
    public void changeMemberData(String username, String job, Long studentId, Long professorId, String university, String department, String email) {
        this.username = username;
        this.job = job;
        this.studentId = studentId;
        this.professorId = professorId;
        this.university = university;
        this.department = department;
        this.email = email;
    }

    public void changeMemberStatus(MemberAuthStatus status){
        this.status = status;
    }

}
