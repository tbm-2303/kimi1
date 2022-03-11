package facades;

import com.google.gson.JsonObject;
import dtos.PersonDTO;
import dtos.RenameMeDTO;
import entities.Person;
import entities.RenameMe;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
//import errorhandling.RenameMeNotFoundException;
import utils.EMF_Creator;

public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }
    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    public PersonDTO getPersonByID(long id) { //throws RenameMeNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person Person = em.find(Person.class, id);
        return new PersonDTO(Person);
    }

    public long getPersonCount() {
        EntityManager em = emf.createEntityManager();
        try {
            return (long) em.createQuery("SELECT COUNT(p) FROM Person p").getSingleResult();
        } finally {
            em.close();
        }
    }

    public JsonObject getPersonInfo(PersonDTO personDTO) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("personId", personDTO.getId());
        jsonObject.addProperty("email", personDTO.getEmail());
        jsonObject.addProperty("firstName", personDTO.getFirstName());
        jsonObject.addProperty("lastName", personDTO.getLastName());
        return jsonObject;
    }

    //
    //
    //
    //
    //
    //
    //
    public PersonDTO create(PersonDTO personDTO) {
        Person person = new Person(personDTO);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

    public RenameMeDTO getById(long id) { //throws RenameMeNotFoundException {
        EntityManager em = emf.createEntityManager();
        RenameMe rm = em.find(RenameMe.class, id);
//        if (rm == null)
//            throw new RenameMeNotFoundException("The RenameMe entity with ID: "+id+" Was not found");
        return new RenameMeDTO(rm);
    }


    public List<PersonDTO> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> personList = query.getResultList();
        return PersonDTO.convertToDTO(personList);
    }

    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade pf = getPersonFacade(emf);
        PersonDTO pdto = pf.getPersonByID(4);
        System.out.println("check getPersonByID(1) :"+pdto.toString());
        JsonObject jsonObject = pf.getPersonInfo(pdto);
        System.out.println(jsonObject.get("personId"));
        System.out.println(jsonObject.get("email"));
    }

}
