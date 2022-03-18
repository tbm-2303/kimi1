package entities;

import dtos.AddressDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "address")
@NamedQuery(name = "Address.deleteAllRows", query = "DELETE from Address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "additional_info")
    private String additionalInfo;

    @OneToMany(mappedBy = "address")
    private List<Person> personList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "cityinfo_id")
    private CityInfo cityInfo;


    public Address() {
    }

    public Address(String street, String additionalInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
    }
    public Address(AddressDTO addressDTO){
        this.street = addressDTO.getStreet();
        this.additionalInfo = addressDTO.getInfo();
    }





    public void addPerson(Person person) {
        this.personList.add(person);
        person.setAddress(this);
    }

    public void removePerson(Person person){
        personList.remove(person);
        person.removeAddress();
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) { this.cityInfo = cityInfo; }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }
}
