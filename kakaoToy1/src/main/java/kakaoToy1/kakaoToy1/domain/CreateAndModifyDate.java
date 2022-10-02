package kakaoToy1.kakaoToy1.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable @Getter
public class CreateAndModifyDate {

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public CreateAndModifyDate() {
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = null;
    }


    //== 편의 메서드
    public void changeModifiedDate(){
        this.modifiedDate = LocalDateTime.now();
    }

    public void setCreatedDate(){
        this.createdDate = LocalDateTime.now();
    }
}
