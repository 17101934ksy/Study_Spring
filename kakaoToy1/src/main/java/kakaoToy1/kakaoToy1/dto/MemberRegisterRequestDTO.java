package kakaoToy1.kakaoToy1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class MemberRegisterRequestDTO {

    private String username;
    private String job;
    private Long uniqueId;
    private String university;
    private String department;
    private String email;

}

