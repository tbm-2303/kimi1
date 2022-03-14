/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import dtos.RenameMeDTO;
import entities.Address;
import entities.CityInfo;
import entities.Person;
import entities.RenameMe;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        CityInfo cityInfo = new CityInfo("12342", "AarhusC");
        Address address = new Address("elmstreet2","12 tv");
        cityInfo.addAddress(address);
        Person person = new Person("api@api.com","api","api");
        address.addPerson(person);


        try {
            em.getTransaction().begin();
            em.persist(cityInfo);
            em.persist(address);
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    public static void main(String[] args) {
        populate();
    }




}
