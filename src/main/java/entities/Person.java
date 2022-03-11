package entities;


import dtos.PersonDTO;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "email")
    private String email;

    @ManyToMany()
    @JoinTable(
            name = "person_hobby",
            joinColumns =
            @JoinColumn(name = "person_id"),
            inverseJoinColumns =
            @JoinColumn(name = "hobby_id"))
    private List<Hobby> hobbies;

    public Person() {
    }

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hobbies = new ArrayList<>();
    }


    public Person dtoPerson(PersonDTO dto) {
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.email = dto.getEmail();
        return this;
    }

    public Person(PersonDTO personDTO) {
        if (personDTO.getId() != null) {
            this.id = personDTO.getId();
        }
        this.firstName = personDTO.getFirstName();
        this.lastName = personDTO.getLastName();
        this.email = personDTO.getEmail();
        this.hobbies = new Hobby().toDtos(personDTO.getHobbies());
    }


    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void addHobby(Hobby hobby) {
        this.hobbies.add(hobby);
        if (hobby != null) {
            hobby.addPersons(this);
        }
    }
}
