package org.example.domain;

import org.example.domain.embed.Address;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "address")
public class AddressEntity {

    @Id @GeneratedValue
    @Column(name = "address_id")
    private Long id;

    private Address address;

    public AddressEntity(){}
    public AddressEntity(String city, String street, String zipcode){
        this.address = new Address(city, street, zipcode);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
