package dtos;

import entities.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<HobbyDTO> hobbies;


    public PersonDTO(Person p) {
        this.id = p.getId();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.email = p.getEmail();
        this.hobbies = HobbyDTO.getDtos(p.getHobbies());
    }
//return list of dtos
    public static List<PersonDTO> getDtos(List<Person> p) {
        List<PersonDTO> pdtos = new ArrayList<>();
        p.forEach(x -> pdtos.add(new PersonDTO(x)));
        return pdtos;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<HobbyDTO> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<HobbyDTO> hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PersonDTO{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", hobbies=").append(hobbies);
        sb.append('}');
        return sb.toString();
    }
}
