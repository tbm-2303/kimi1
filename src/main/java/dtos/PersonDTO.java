package dtos;

import com.google.gson.JsonObject;
import entities.Hobby;
import entities.Person;
import entities.Phone;

import java.util.ArrayList;
import java.util.List;

public class PersonDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private List<HobbyDTO> hobbiesDTOS = new ArrayList<>();
    private List<Phone> phoneList = new ArrayList<>();
    private AddressDTO addressDTO;


    public PersonDTO(String email, String firstName, String lastName, AddressDTO addressDTO) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressDTO = addressDTO;
    }

    public PersonDTO(Person person) {
        if (person.getId() != null) {
            this.id = person.getId();
        }
        this.email = person.getEmail();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.addressDTO = new AddressDTO(person.getAddress());
        for (Hobby h : person.getHobbylist()) {
            this.hobbiesDTOS.add(new HobbyDTO(h));
        }
    }

    public static List<PersonDTO> convertToDTO(List<Person> persons) {
        List<PersonDTO> personDTOS = new ArrayList<>();
        persons.forEach(p -> personDTOS.add(new PersonDTO(p)));
        return personDTOS;
    }


    public void setHobbiesDTOS(List<HobbyDTO> hobbiesDTOS) {
        this.hobbiesDTOS = hobbiesDTOS;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
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

    public List<HobbyDTO> getHobbiesDTOS() {
        return hobbiesDTOS;
    }

    public void setHobbies(List<HobbyDTO> hobbies) {
        this.hobbiesDTOS = hobbies;
    }

    @Override
    public String toString() {
        return "PersonDTO{" + "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", hobbies=" + hobbiesDTOS +
                '}';
    }
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("personID", getId());
        jsonObject.addProperty("email", getEmail());
        jsonObject.addProperty("firstName", getFirstName());
        jsonObject.addProperty("lastName", getLastName());
        jsonObject.addProperty("addressId", getAddressDTO().getId());
        jsonObject.addProperty("street", getAddressDTO().getStreet());
        jsonObject.addProperty("additionalInfo", getAddressDTO().getAdditionalInfo());
        jsonObject.addProperty("cityInfoId", getAddressDTO().getCityInfoDTO().getId());
        jsonObject.addProperty("zipCode", getAddressDTO().getCityInfoDTO().getZipCode());
        jsonObject.addProperty("city", getAddressDTO().getCityInfoDTO().getCity());
        return jsonObject;
    }
}
