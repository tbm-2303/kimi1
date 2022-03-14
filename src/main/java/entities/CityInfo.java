package entities;

import dtos.CityInfoDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cityinfo")
public class CityInfo {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "cityInfo")
    private List<Address> addressList = new ArrayList<>();


    public CityInfo() {}

    public CityInfo(String zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
    }

    public CityInfo(CityInfoDTO cityInfoDTO) {
        this.zipCode = cityInfoDTO.getZipCode();
        this.city = cityInfoDTO.getCity();
    }


    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void addAddress(Address address) {
        this.addressList.add(address);
        address.setCityInfo(this);
    }
}
