package facades;

import dtos.*;
import entities.*;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import errorhandling.EntityNotFoundException;
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

    public PersonDTO getPersonByID(long id) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, id);
        if (person == null)
            throw new EntityNotFoundException("The Person entity with ID: '"+id+"' was not found");
        return new PersonDTO(person);
    }

    public long getPersonCount() {
        EntityManager em = emf.createEntityManager();
        try {
            return (long) em.createQuery("SELECT COUNT(p) FROM Person p").getSingleResult();
        } finally {
            em.close();
        }
    }


    public CityInfo searchZips(String zipcode, EntityManager em) {
        if (!zipcode.equals("")) {
            TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c where c.zipCode = :zipcode", CityInfo.class);
            query.setParameter("zipcode", zipcode);
            List<CityInfo> c = query.getResultList();
            return c.get(0);
        }
        return null;
    }
    public void linkHobbyPerson(PersonDTO p, HobbyDTO h){
        EntityManager em = emf.createEntityManager();
        Person person = new Person(p);
        Hobby hobby = new Hobby(h);
        person.addHobby(hobby);
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public HobbyDTO createHobby(HobbyDTO hobbyDTO){
        EntityManager em = emf.createEntityManager();
        Hobby hobby = new Hobby(hobbyDTO);
        try {
            em.getTransaction().begin();
            em.persist(hobby);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new HobbyDTO(hobby);
    }

    public PersonDTO create(PersonDTO personDTO) {
        EntityManager em = emf.createEntityManager();
        CityInfo cityInfo = new CityInfo(personDTO.getAddressDTO().getCityInfoDTO());
        Address address = new Address(personDTO.getAddressDTO());
        Person person = new Person(personDTO);
        cityInfo.addAddress(address);
        address.addPerson(person);
        for (PhoneDTO phoneDTO : personDTO.getPhoneList()) {
            person.addPhone(new Phone(phoneDTO));
        }
        try {
            em.getTransaction().begin();
            em.persist(cityInfo);
            em.persist(address);
            em.persist(person);
            for (Phone phone : person.getPhoneList()) {
                em.persist(phone);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }


    public PersonDTO update(PersonDTO personDTO) {
        EntityManager em = emf.createEntityManager();
        Person newPerson = new Person(personDTO);
        //Remember to make relations in the new person.
        //Also remember to get a ref to the old person. Then edit all the relations to the old person.
        //Then merge
        try {
            em.getTransaction().begin();
            em.merge(newPerson);
            em.getTransaction().commit();
            return new PersonDTO(newPerson);
        } catch (Exception e) {
            throw new WebApplicationException("Transaction failed", 500);
        } finally {
            em.close();
        }
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

    public PersonDTO getByPhoneNumber(String phoneNumber) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> typedQuery = em.createQuery("SELECT p FROM Phone ph JOIN ph.person p WHERE ph.number = " + phoneNumber, Person.class);
        if (typedQuery.getResultList().size() == 0)
            throw new EntityNotFoundException("The Person entity with phone number: '"+phoneNumber+"' was not found");
        Person person = typedQuery.getSingleResult();
        return new PersonDTO(person);
    }

    public List<PersonDTO> getPersonsWithHobby(long hobbyId) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> typedQueryPerson = em.createQuery("SELECT p FROM Person p LEFT JOIN p.hobbylist h WHERE h.id=" + hobbyId, Person.class);
        List<Person> personList = typedQueryPerson.getResultList();
        List<PersonDTO> personDTOList = new ArrayList<>();
        for (Person p : personList) {
            personDTOList.add(new PersonDTO(p));
        }
        return personDTOList;
    }

    public PersonDTO addHobbyToPerson(long id, HobbyDTO hobbyDTO) {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, id);
        Hobby hobby = new Hobby(hobbyDTO);
        person.addHobby(hobby);
        try {
            em.getTransaction().begin();
            em.persist(hobby);
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }


    public static void main(String[] args) throws EntityNotFoundException {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade pf = getPersonFacade(emf);
        PersonDTO persondto = pf.getByPhoneNumber("91405485");
        System.out.println(persondto.toString());

    }



}
