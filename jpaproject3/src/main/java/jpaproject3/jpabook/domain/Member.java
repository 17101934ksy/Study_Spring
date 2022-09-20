package jpaproject3.jpabook.domain;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class Member extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(length = 10, nullable = false, unique = true)
    private String name;

    @Column(length = 30)
    private String street;
    private String zipcode;

    @OneToOne
    @JoinColumn(name="lock_id", unique = true)
    private Locker locker;

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();

    public Member(){}

    public Member(String name, String street, String zipcode){
        this.name = name;
        this.street = street;
        this.zipcode = zipcode;
    }

    /*
    *  custom method
    * */


    /*
    * getter setter
    * */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
