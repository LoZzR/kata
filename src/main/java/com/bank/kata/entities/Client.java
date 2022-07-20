package com.bank.kata.entities;

import com.bank.kata.utils.DateProcessor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "CLIENTS")
@SequenceGenerator(
        name = "ID_GENERATOR", sequenceName="S_CLIENT",allocationSize=5,initialValue=1
)
public class Client implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ID_GENERATOR"
    )
    @Column(name = "CLIENT_ID")
    private Long id;

    private String identifier;

    private String firstName;

    private String lastName;

    @DateTimeFormat(pattern = DateProcessor.DATE_FORMAT)
    private LocalDateTime birthdate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street",
                    column = @Column(name = "CLIENT_STREET")),
            @AttributeOverride(name = "zipcode",
                    column = @Column(name = "CLIENT_ZIPCODE", length = 5)),
            @AttributeOverride(name = "city",
                    column = @Column(name = "CLIENT_CITY"))
    })
    private Address address;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Account> accounts;

    public Client() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address adress) {
        this.address = adress;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", identifier='" + identifier + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate=" + birthdate + '\'' +
                ", adress=" + address +
                '}';
    }
}
