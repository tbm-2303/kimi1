package dtos;

import entities.CityInfo;

import java.util.ArrayList;
import java.util.List;

public class CityInfoDTO {
    private long id;
    private String zipCode;
    private String city;

    public CityInfoDTO(String zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
    }

    public CityInfoDTO(CityInfo cityInfo) {
        if (cityInfo.getId() != null) {
            this.id = cityInfo.getId();
        }
        this.zipCode = cityInfo.getZipCode();
        this.city = cityInfo.getCity();
    }

    public static List<CityInfoDTO> ConvertToDTOS(List<CityInfo> cityInfoList) {
        List<CityInfoDTO> cityInfoDTOList = new ArrayList<>();
        cityInfoList.forEach(ci -> cityInfoDTOList.add(new CityInfoDTO(ci)));
        return cityInfoDTOList;
    }




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "CityInfoDTO{" +
                "id=" + id +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
