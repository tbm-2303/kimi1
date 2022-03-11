/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import dtos.RenameMeDTO;
import entities.Person;
import entities.RenameMe;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade pf = PersonFacade.getPersonFacade(emf);
     //   System.out.println(pf.create(new PersonDTO(new Person("timmy","busk","timmy_busk@hotmail.com"))).toString());
        System.out.println(pf.getPersonByID(1));
        System.out.println(pf.getPersonByID(2));
    }
    
    public static void main(String[] args) {
        populate();
    }




}
