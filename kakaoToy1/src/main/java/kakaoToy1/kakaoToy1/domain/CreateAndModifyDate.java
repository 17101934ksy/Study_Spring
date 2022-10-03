package kakaoToy1.kakaoToy1.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Embeddable @Getter
public class CreateAndModifyDate {

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public CreateAndModifyDate() {
        this.createdDate = now();
        this.modifiedDate = null;
    }


    //== 편의 메서드
    public void changeModifiedDate(){
        this.modifiedDate = now();
    }

    public void setCreatedDate(){
        this.createdDate = now();
    }
}
