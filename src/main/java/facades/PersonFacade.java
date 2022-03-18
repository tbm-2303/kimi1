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
            throw new EntityNotFoundException("The Person entity with ID: '" + id + "' was not found");
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


    public HobbyDTO createHobby(HobbyDTO hobbyDTO) {
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

    public boolean sameAddress(PersonDTO personDTO, Address address) {
        return personDTO.getAddressDTO().getStreet().equals(address.getStreet()) && personDTO.getAddressDTO().getAdditionalInfo().equals(address.getAdditionalInfo());
    }

    public AddressDTO getOrCreate(AddressDTO addressDTO) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Address> query = em.createQuery(
                "SELECT a FROM Address a, CityInfo c " +
                        "WHERE a.street = " +
                        " '" + addressDTO.getStreet() + "' " +
                        "and a.additionalInfo = " +
                        " '" + addressDTO.getAdditionalInfo() + "' " +
                        "and c.zipCode = " +
                        " '" + addressDTO.getCityInfoDTO().getZipCode() + "' ", Address.class);

        List<Address> addressList = query.getResultList();
        if (addressList.isEmpty()) {
            return addressDTO;
        }
        return new AddressDTO(addressList.get(0));
    }

    public PersonDTO update(PersonDTO personDTO) {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, personDTO.getId());//person som vi gerne vil opdatere
        Address address = person.getAddress();
        CityInfo cityInfo = person.getAddress().getCityInfo();
        CityInfo c = em.find(CityInfo.class, personDTO.getAddressDTO().getCityInfoDTO().getId());//find city object som matcher med input
        //opdater person
        person.setEmail(personDTO.getEmail());
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
//opdater addresse
        if (!sameAddress(personDTO, address)) {
            address.setStreet(personDTO.getAddressDTO().getStreet());//add new data
            address.setAdditionalInfo(personDTO.getAddressDTO().getAdditionalInfo());//add new data
            address.setCityInfo(c);
            //c.addAddress(address);
        }
        //hobby and phone stuff but just check if address works first
        //
        try {
            em.getTransaction().begin();
            em.merge(cityInfo);
            em.merge(address);
            em.merge(person);
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

    //1
    public PersonDTO getByPhoneNumber(String phoneNumber) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> typedQuery = em.createQuery("SELECT p FROM Phone ph JOIN ph.person p WHERE ph.number = " + phoneNumber, Person.class);
        if (typedQuery.getResultList().size() == 0)
            throw new EntityNotFoundException("The Person entity with phone number: '" + phoneNumber + "' was not found");
        Person person = typedQuery.getSingleResult();
        return new PersonDTO(person);
    }

    //2
    public List<PersonDTO> getPersonsWithHobby(long hobbyId) {
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

    public PersonDTO addHobbyToPersonByIds(long person_id, long hobby_id) {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, person_id);
        Hobby hobby = em.find(Hobby.class, hobby_id);
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


    }



}
