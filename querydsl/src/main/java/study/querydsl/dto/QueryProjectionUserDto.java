package study.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QueryProjectionUserDto {

    private String username;
    private int age;

    @QueryProjection
    public QueryProjectionUserDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
