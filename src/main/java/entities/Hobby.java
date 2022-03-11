package entities;

import dtos.HobbyDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hobby")
public class Hobby {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "hobbies")
    private List<Person> persons = new ArrayList<>();

    public Hobby() {
    }


    public Hobby(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Hobby(HobbyDTO hobbyDTO) {
        this.name = hobbyDTO.getName();
        this.description = hobbyDTO.getDescription();
    }

    public void addPersons(Person person) {
        this.persons.add(person);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "Hobby{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", persons=" + persons +
                '}';
    }
}
