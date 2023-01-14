package study.querydsl.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QueryUserDto {

    private String name;
    private int age;

    public QueryUserDto(String username, int age) {
        this.name = username;
        this.age = age;
    }
}
