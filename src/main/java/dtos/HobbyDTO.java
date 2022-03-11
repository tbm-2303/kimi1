package dtos;

import entities.Hobby;
import java.util.ArrayList;
import java.util.List;

public class HobbyDTO {
    private Long id;
    private String name;
    private String description;



    public HobbyDTO(Hobby hobby) {
        if (hobby.getId() != null) {
            this.id = hobby.getId();
        }
        this.name = hobby.getName();
        this.description = hobby.getDescription();
    }

    public static List<HobbyDTO> getDtos(List<Hobby> hobbies) {
        List<HobbyDTO> hobbies_dto = new ArrayList();
        hobbies.forEach(rm -> hobbies_dto.add(new HobbyDTO(rm)));
        return hobbies_dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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




}
