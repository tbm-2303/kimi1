package dtos;

import entities.Phone;

import java.util.ArrayList;
import java.util.List;

public class PhoneDTO {
    private long id;
    private String number;
    private String description;
    private PersonDTO personDTO;

    public PhoneDTO(String number, String description) {
        this.number = number;
        this.description = description;

    }
    public PhoneDTO(Phone p) {
        if (p.getId() != null) {
            this.id = p.getId();
        }
        this.number = p.getNumber();
        this.description = p.getDescription();
    }

    public static List<PhoneDTO> convertToDTO(List<Phone> phones) {
        List<PhoneDTO> phoneDTOS = new ArrayList<>();

        //for (Phone p: phones) { apparently using stupid functional expression is better. but is like my old for each things :(
         //   phoneDTOS.add(new PhoneDTO(p));
      //  }
        phones.forEach(p -> phoneDTOS.add(new PhoneDTO(p)));
        return phoneDTOS;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public PersonDTO getPersonDTO() {
        return personDTO;
    }

    public void setPersonDTO(PersonDTO personDTO) {
        this.personDTO = personDTO;
    }
}