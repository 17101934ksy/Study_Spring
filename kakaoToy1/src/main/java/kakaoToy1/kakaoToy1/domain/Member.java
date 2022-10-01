package kakaoToy1.kakaoToy1.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@ToString
public class Member implements Persistable<String> {

    @Id @GeneratedValue
    @Column(name = "memberId")
    private Long id;

    @Enumerated(EnumType.STRING)
    private MemberLoginWay memberLoginWay;

    @CreatedDate
    private LocalDateTime createdDate;

    private String loginId;

    private String job;

    private Long uniqueId;

    private String username;

    private String university;

    private String department;

    private String email;

    @Enumerated(EnumType.STRING)
    private MemberAuthStatus status;

    public Member() {}

    public Member(MemberLoginWay memberLoginWay, String loginId) {
        this.memberLoginWay = memberLoginWay;
        this.loginId = loginId;
        this.status = MemberAuthStatus.GUEST;
    }

    //== 필드값 주입 메서드
    public void changeMemberData(String username, String job, Long uniqueId, String university, String department, String email) {
        this.username = username;
        this.job = job;
        this.uniqueId = uniqueId;
        this.university = university;
        this.department = department;
        this.email = email;
    }

    public void changeMemberStatus(MemberAuthStatus status){
        this.status = status;
    }

    // Persistable 인터페이스 오버라이드 login_id -> String


    @Override
    public String getId(){
        return loginId;
    }

    @Override
    public boolean isNew() {
        return createdDate == null;
    }
}
