package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.ejb.UserRepository;
import com.realdolmen.redoair.entities.Address;
import com.realdolmen.redoair.entities.User;
import com.realdolmen.redoair.entities.UserType;
import org.junit.Before;
import org.junit.Test;

public class UserPersistenceTest extends DataPersistenceTest{

    private UserRepository ur;

    @Before
    public void before(){
        ur = new UserRepository();
        ur.setEntityManager(entityManager());
    }

    @Test
    public void createUsers() {
        User u1 = new User();
        User u2 = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt@realdolmen.com");
        User u3 = new User("MDhondt2", "123", UserType.EMPLOYEE, "maarten.dhondt@realdolmen.com");
        User u4 = new User("MDhondt3", "123", UserType.PARTNER, "maarten.dhondt@realdolmen.com");
        User u5 = new User("MDhondt4", "Maarten", "Dhondt", "123", new Address("Croix ou Pile 1", "Saint-Sauveur", "7912", "Belgium"), UserType.EMPLOYEE,"maarten.dhondt@realdolmen.com");
    }

    @Test
    public void userCanBePersisted(){
        User u1 = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt1@realdolmen.com");
        User u2 = new User("MDhondt2", "123", UserType.EMPLOYEE, "maarten.dhondt2@realdolmen.com");
        User u3 = new User("MDhondt3", "123", UserType.PARTNER, "maarten.dhondt3@realdolmen.com");
        ur.createUser(u1);
        ur.createUser(u2);
        ur.createUser(u3);
        ur.getEntityManager().flush();
        assertNotNull(u1.getId());
        assertNotNull(u2.getId());
        assertNotNull(u3.getId());
    }

}
