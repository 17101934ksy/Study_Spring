package kakaoToy1.kakaoToy1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberRegisterRequestDTO {

    private String username;
    private String job;
    private Long studentId;
    private Long professorId;
    private String university;
    private String department;
    private String email;

}

