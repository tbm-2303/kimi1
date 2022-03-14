package entities;


import dtos.PersonDTO;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
@NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person")
public class Person {


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
    private List<Hobby> hobbylist = new ArrayList<>();

    @OneToMany(mappedBy = "person")
    private List<Phone> phoneList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;



    public Person() {
    }

    public Person(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public Person(PersonDTO pDTO) {
        this.email = pDTO.getEmail();
        this.firstName = pDTO.getFirstName();
        this.lastName = pDTO.getLastName();
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Hobby> getHobbylist() {
        return hobbylist;
    }

    public void setHobbylist(List<Hobby> hobbies) {
        this.hobbylist = hobbies;
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
        this.hobbylist.add(hobby);
        hobby.addPersons(this);
    }
    public void addPhone(Phone phone){
        this.phoneList.add(phone);
        phone.setPerson(this);
    }

}
