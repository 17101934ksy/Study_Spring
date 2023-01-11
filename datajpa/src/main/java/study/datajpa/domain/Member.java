package study.datajpa.domain;

import lombok.*;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@ToString(of = {"id", "username", "age"})
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }


    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            updateTeam(team);
        }
    }



    public void updateUsername(String username) {
        this.username = username;
    }

    public void updateAge(int age) {
        this.age = age;
    }

    public void updateTeam(Team team) {
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }

        this.team = team;
        team.getMembers().add(this);
    }
}
