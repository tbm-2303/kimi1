package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dtos.*;
import entities.Hobby;
import entities.Person;
import errorhandling.EntityNotFoundException;
import utils.EMF_Creator;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
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

    //get amount of persons
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonCount() {
        long count = FACADE.getPersonCount();
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }
    //get all persons
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPersons() {
        List<PersonDTO> personList = FACADE.getAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (PersonDTO x : personList) {
            stringBuilder.append(x.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    //get Person by id
    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonByID(@PathParam("id") long id) throws EntityNotFoundException {
        PersonDTO personDTO = FACADE.getPersonByID(id);
        return Response.ok().entity(GSON.toJson(personDTO)).build();
    }


    //get person by phone
    @Path("phone/{number}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getPersonByPhone(@PathParam("number") String number) throws EntityNotFoundException {
        PersonDTO personDTO = FACADE.getByPhoneNumber(number);
        return Response.ok().entity(GSON.toJson(personDTO)).build();
    }
    // Get all persons with a given hobby ID
    @Path("hobby/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonsWithHobby(@PathParam("id") long id) throws EntityNotFoundException {
        List<PersonDTO> personsWithHobby = FACADE.getPersonsWithHobby(id);
        return Response.ok().entity(GSON.toJson(personsWithHobby)).build();
    }
    //update person
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String update(String person) {
        PersonDTO newPersonDTO = GSON.fromJson(person, PersonDTO.class);
        PersonDTO updatedPersonDTO = FACADE.update(newPersonDTO);
        return GSON.toJson(updatedPersonDTO);
    }

    //create new person
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addPerson(String person) {
        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
        PersonDTO newperson = FACADE.create(personDTO);
        return Response.ok().entity(GSON.toJson(newperson)).build();
    }
    //new hobby
    @Path("hobby")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addHobby(String hobby) {
        HobbyDTO hobbyDTO = GSON.fromJson(hobby, HobbyDTO.class);
        HobbyDTO newhobby = FACADE.createHobby(hobbyDTO);
        return Response.ok().entity(GSON.toJson(newhobby)).build();
    }

    //add hobby to person
    @Path("addhobby/{person_id}")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addHobbyToPerson(@PathParam("person_id") long person_id, String hobby) {
        HobbyDTO hobbyDTO = GSON.fromJson(hobby, HobbyDTO.class);
        PersonDTO person = FACADE.addHobbyToPerson(person_id, hobbyDTO);
        return Response.ok().entity(GSON.toJson(person)).build();
    }

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







