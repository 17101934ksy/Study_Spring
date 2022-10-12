package kakaoToy1.kakaoToy1.domain.member;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
public class School {

    @Id @GeneratedValue
    @Column(name = "school_id")
    private Long id;

    private String schoolName;
}
