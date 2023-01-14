package study.querydsl.dto;

import lombok.Data;

@Data
public class MemberSearchCond {
    // 회원명 팀명 나이
    private String username;
    private String teamName;
    private Integer ageGoe;
    private Integer ageLoe;
}
