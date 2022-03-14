package dtos;

import entities.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressDTO {
    private long id;
    private String street;
    private String additionalInfo;
    //private List<PersonDTO> personDTOList;
    private CityInfoDTO cityInfoDTO;


    public AddressDTO(String street, String additionalInfo, CityInfoDTO cityDTO) {
        this.street = street;
        this.additionalInfo = additionalInfo;
        this.cityInfoDTO = cityDTO;
    }

    public AddressDTO(Address address) {
        if (address.getId() != null) {
            this.id  = address.getId();
        }
        this.street = address.getStreet();
        this.additionalInfo = address.getAdditionalInfo();
        //address.getPersonList().forEach(p -> personDTOList.add(new PersonDTO(p)));
        this.cityInfoDTO = new CityInfoDTO(address.getCityInfo());
    }

    public static List<AddressDTO> convertToDTO(List<Address> list) {
        List<AddressDTO> addressDTOS = new ArrayList<>();
        list.forEach(address -> addressDTOS.add(new AddressDTO(address)));
        return addressDTOS;
    }




    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public CityInfoDTO getCityInfoDTO() {
        return cityInfoDTO;
    }

    public void setCityInfoDTO(CityInfoDTO cityInfoDTO) {
        this.cityInfoDTO = cityInfoDTO;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }


}
