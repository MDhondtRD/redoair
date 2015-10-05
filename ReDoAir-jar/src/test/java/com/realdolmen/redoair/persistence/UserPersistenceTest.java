package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.ejb.UserRepository;
import com.realdolmen.redoair.entities.Address;
import com.realdolmen.redoair.entities.User;
import com.realdolmen.redoair.entities.UserType;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.PersistenceException;

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
        User u5 = new User("MDhondt4", "Maarten", "Dhondt", "123", new Address("Croix ou Pile 1", null, "Saint-Sauveur", "7912", "Belgium"), UserType.EMPLOYEE,"maarten.dhondt@realdolmen.com", null);
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

    @Test
    public void usersCanBeRemoved(){
        assertEquals(1, ur.getAllUsers().size());
        User u1 = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt1@realdolmen.com");
        User u2 = new User("MDhondt2", "123", UserType.EMPLOYEE, "maarten.dhondt2@realdolmen.com");
        User u3 = new User("MDhondt3", "123", UserType.PARTNER, "maarten.dhondt3@realdolmen.com");
        ur.createUser(u1); ur.createUser(u2); ur.createUser(u3); ur.getEntityManager().flush();
        assertEquals(4, ur.getAllUsers().size());
        ur.deleteUser(u1);
        ur.deleteUser(u2);
        ur.getEntityManager().flush();
        assertEquals(2, ur.getAllUsers().size());
        ur.deleteUser(u3);
        ur.deleteUser(ur.getUserByUsername("MDhondt"));
        ur.getEntityManager().flush();
        assertEquals(0, ur.getAllUsers().size());
    }

    @Test(expected = PersistenceException.class)
    public void userNameMustBeUnique1(){
        // database already has a user "MDhondt"
        User u = new User("MDhondt", "123", UserType.CUSTOMER, "maarten.dhondt1@realdolmen.com");
        ur.createUser(u);
        ur.getEntityManager().flush();
    }

    @Test(expected = PersistenceException.class)
    public void userNameMustBeUnique2(){
        User u1 = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt1@realdolmen.com");
        User u2 = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt2@realdolmen.com");
        ur.createUser(u1);
        ur.createUser(u2);
        ur.getEntityManager().flush();
    }

    @Test(expected = PersistenceException.class)
    public void emailMustBeUnique1(){
        // database already has a user with email "maarten.dhondt@skynet.be"
        User u = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt@skynet.be");
        ur.createUser(u);
        ur.getEntityManager().flush();
    }

    @Test(expected = PersistenceException.class)
    public void emailMustBeUnique2(){
        User u1 = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt@realdolmen.com");
        User u2 = new User("MDhondt2", "123", UserType.CUSTOMER, "maarten.dhondt@realdolmen.com");
        ur.createUser(u1);
        ur.createUser(u2);
        ur.getEntityManager().flush();
    }

    @Test
    public void usersCanBeRetrieved(){
        assertEquals(1, ur.getAllUsers().size());

        User u1 = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt1@realdolmen.com");
        User u2 = new User("MDhondt2", "123", UserType.CUSTOMER, "maarten.dhondt2@realdolmen.com");
        ur.createUser(u1);
        ur.createUser(u2);
        ur.getEntityManager().flush();
        assertEquals(3, ur.getAllUsers().size());
    }

    @Test
    public void usersCanBeRetrievedByType(){
        assertEquals(1, ur.getAllUsersOfType(UserType.CUSTOMER).size());
        assertEquals(0, ur.getAllUsersOfType(UserType.EMPLOYEE).size());
        assertEquals(0, ur.getAllUsersOfType(UserType.PARTNER).size());

        User u1 = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt1@realdolmen.com");
        User u2 = new User("MDhondt2", "123", UserType.EMPLOYEE, "maarten.dhondt2@realdolmen.com");
        User u3 = new User("MDhondt3", "123", UserType.PARTNER, "maarten.dhondt3@realdolmen.com");
        User u4 = new User("MDhondt4", "123", UserType.CUSTOMER, "maarten.dhondt4@realdolmen.com");
        User u5 = new User("MDhondt5", "123", UserType.EMPLOYEE, "maarten.dhondt5@realdolmen.com");
        ur.createUser(u1); ur.createUser(u2); ur.createUser(u3); ur.createUser(u4); ur.createUser(u5);
        ur.getEntityManager().flush();

        assertEquals(3, ur.getAllUsersOfType(UserType.CUSTOMER).size());
        assertEquals(2, ur.getAllUsersOfType(UserType.EMPLOYEE).size());
        assertEquals(1, ur.getAllUsersOfType(UserType.PARTNER).size());
    }

    @Test
    public void usersCanBeRetrievedById(){
        assertNotNull(ur.getUserById(1));
        User u = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt@realdolmen.com");
        ur.createUser(u); ur.getEntityManager().flush();
        assertEquals(u, ur.getUserById(u.getId()));
    }

    @Test
    public void usersCanBeRetrievedByUsername(){
        assertNotNull(ur.getUserByUsername("MDhondt"));
        User u = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt@realdolmen.com");
        ur.createUser(u); ur.getEntityManager().flush();
        assertEquals(u, ur.getUserByUsername(u.getUsername()));
    }

    @Test
    public void usersCanBeRetrievedByEmail(){
        assertNotNull(ur.getUserByEmail("maarten.dhondt@skynet.be"));
        User u = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt@realdolmen.com");
        ur.createUser(u); ur.getEntityManager().flush();
        assertEquals(u, ur.getUserByEmail(u.getEmail()));
    }



}
