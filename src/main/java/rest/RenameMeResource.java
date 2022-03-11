package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import facades.PersonFacade;
import utils.EMF_Creator;
import facades.FacadeExample;

import javax.persistence.EntityManagerFactory;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class RenameMeResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final FacadeExample FACADE = FacadeExample.getFacadeExample(EMF);
    private static final PersonFacade personfacade = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";

    }

    @GET
    @Path("count")
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getRenameMeCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public String getPersonByID(@PathParam("id") long id) {

        PersonDTO pd = personfacade.getPersonByID(id);
        String person = pd.toString();
        System.out.println(person);
        return person;
    }
}
