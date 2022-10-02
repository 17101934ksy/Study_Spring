package kakaoToy1.kakaoToy1.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable @Getter
public class CreateAndModifyDate {

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //== 편의 메서드
    public void changeModifiedDate(){
        this.modifiedDate=LocalDateTime.now();
    }

    protected void setCreatedDate(){
        this.createdDate = LocalDateTime.now();
    }
}
