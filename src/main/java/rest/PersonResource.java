package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dtos.PersonDTO;
import utils.EMF_Creator;
import facades.PersonFacade;

import javax.persistence.EntityManagerFactory;
import javax.websocket.server.PathParam;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

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

    @Path("test")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String test() {
       String test = "test";
        return "{\"count\":" + test + "}";
    }

    @Path("addperson")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addPerson(String jsonContext) {
        JsonObject jsonObject = GSON.fromJson(jsonContext, JsonObject.class);
        // Person
        String email = jsonObject.get("email").getAsString();
        String firstName = jsonObject.get("firstName").getAsString();
        String lastName = jsonObject.get("lastName").getAsString();
        PersonDTO personDTO = new PersonDTO(email, firstName, lastName);
        PersonDTO persistedPerson = FACADE.create(personDTO);

        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(persistedPerson.toJson()))
                .build();
    }
/*
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





