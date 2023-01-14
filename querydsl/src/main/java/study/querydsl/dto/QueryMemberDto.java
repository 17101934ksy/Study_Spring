package study.querydsl.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QueryMemberDto {

    private String username;
    private int age;

    public QueryMemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
