package entities;

import dtos.PhoneDTO;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class Phone {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name ="number")
    private String number;

    @Column(name ="description")
    private  String description;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;


    public Phone() {
    }

    public Phone(String number, String description) {
        this.number = number;
        this.description = description;
    }
    public Phone(PhoneDTO phoneDTO){
        this.number = phoneDTO.getNumber();
        this.description = phoneDTO.getDescription();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }
}
