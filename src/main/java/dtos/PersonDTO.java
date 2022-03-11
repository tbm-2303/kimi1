package dtos;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entities.Hobby;
import entities.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private List<HobbyDTO> hobbies_dto = new ArrayList<>();


    public PersonDTO(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public PersonDTO(Person person) {
        if (person.getId() != null) {
            this.id = person.getId();
        }
        this.email = person.getEmail();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        for (Hobby h : person.getHobbies()) {
            this.hobbies_dto.add(new HobbyDTO(h));
        }
    }
    public static List<PersonDTO> getDtos(List<Person> persons) {
        List<PersonDTO> pDtos = new ArrayList<>();
        persons.forEach(p -> pDtos.add(new PersonDTO(p)));
        return pDtos;
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

    public List<HobbyDTO> getHobbies_dto() {
        return hobbies_dto;
    }

    public void setHobbies(List<HobbyDTO> hobbies) {
        this.hobbies_dto = hobbies;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PersonDTO{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", hobbies=").append(hobbies_dto);
        sb.append('}');
        return sb.toString();
    }
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("personID", getId());
        jsonObject.addProperty("email", getEmail());
        jsonObject.addProperty("firstName", getFirstName());
        jsonObject.addProperty("lastName", getLastName());
        return jsonObject;
    }
}
