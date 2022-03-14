package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dtos.AddressDTO;
import dtos.CityInfoDTO;
import dtos.PersonDTO;
import utils.EMF_Creator;
import facades.PersonFacade;

import javax.persistence.EntityManagerFactory;
import javax.websocket.server.PathParam;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonCount() {
        long count = FACADE.getPersonCount();
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }


    @Path("addperson")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addPersontest(String jsonContext) {
        JsonObject jsonObject = GSON.fromJson(jsonContext, JsonObject.class);
        // Person
        String email = jsonObject.get("email").getAsString();
        String firstName = jsonObject.get("firstName").getAsString();
        String lastName = jsonObject.get("lastName").getAsString();
        // Address
        String street = jsonObject.get("street").getAsString();
        String additionalInfo = jsonObject.get("additionalInfo").getAsString();
        // CityInfo
        String zipCode = jsonObject.get("zipCode").getAsString();
        String city = jsonObject.get("city").getAsString();

        CityInfoDTO cityInfoDTO = new CityInfoDTO(zipCode, city);
        AddressDTO addressDTO = new AddressDTO(street, additionalInfo, cityInfoDTO);
        PersonDTO personDTO = new PersonDTO(email, firstName, lastName, addressDTO);
        PersonDTO persistedPerson = FACADE.create(personDTO);

        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(persistedPerson.toJson()))
                .build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String update(String p) {
        PersonDTO newPersonDTO = GSON.fromJson(p, PersonDTO.class);
        PersonDTO updatedPersonDTO = FACADE.update(newPersonDTO);
        return GSON.toJson(updatedPersonDTO);
    }

    @Path("test")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String test() {
       String test = "test";
        return "{\"count\":" + test + "}";
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPersons() {
    List<PersonDTO> personList = FACADE.getAll();
    StringBuilder stringBuilder = new StringBuilder();
        for (PersonDTO x: personList) {
            stringBuilder.append(x.toString()).append("\n");
        }
        return stringBuilder.toString();
    }



/*
// Get all info from a person by person ID
    @Path("info{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@Consumes({MediaType.APPLICATION_JSON})
    public Response getInfoId(@PathParam("id") long id) {
        PersonDTO personDTO = FACADE.getPersonByID(id);
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(FACADE.getPersonInfo(personDTO)))
                .build();
    }
    // Get all info from a person by person ID
    @Path("info{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getInfoId(@PathParam("id") long id, String jsonContext) {
        PersonDTO personDTO = FACADE.getPersonByID(id);
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(FACADE.getPersonInfo(personDTO)))
                .build();
    }

 */

}





