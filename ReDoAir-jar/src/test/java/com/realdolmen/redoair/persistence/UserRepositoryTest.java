package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.ejb.UserRepository;
import com.realdolmen.redoair.entities.Address;
import com.realdolmen.redoair.entities.User;
import com.realdolmen.redoair.entities.UserType;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.PersistenceException;

public class UserRepositoryTest extends DataPersistenceTest{

    private UserRepository repo;

    @Before
    public void before(){
        repo = new UserRepository();
        repo.setEntityManager(entityManager());
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
        repo.createUser(u1);
        repo.createUser(u2);
        repo.createUser(u3);
        repo.getEntityManager().flush();
        assertNotNull(u1.getId());
        assertNotNull(u2.getId());
        assertNotNull(u3.getId());
    }

    @Test
    public void usersCanBeRemoved(){
        assertEquals(1, repo.getAllUsers().size());
        User u1 = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt1@realdolmen.com");
        User u2 = new User("MDhondt2", "123", UserType.EMPLOYEE, "maarten.dhondt2@realdolmen.com");
        User u3 = new User("MDhondt3", "123", UserType.PARTNER, "maarten.dhondt3@realdolmen.com");
        repo.createUser(u1); repo.createUser(u2); repo.createUser(u3); repo.getEntityManager().flush();
        assertEquals(4, repo.getAllUsers().size());
        repo.deleteUser(u1);
        repo.deleteUser(u2);
        repo.getEntityManager().flush();
        assertEquals(2, repo.getAllUsers().size());
        repo.deleteUser(u3);
        repo.deleteUser(repo.getUserByUsername("MDhondt"));
        repo.getEntityManager().flush();
        assertEquals(0, repo.getAllUsers().size());
    }

    @Test(expected = PersistenceException.class)
    public void userNameMustBeUnique1(){
        // database already has a user "MDhondt"
        User u = new User("MDhondt", "123", UserType.CUSTOMER, "maarten.dhondt1@realdolmen.com");
        repo.createUser(u);
        repo.getEntityManager().flush();
    }

    @Test(expected = PersistenceException.class)
    public void userNameMustBeUnique2(){
        User u1 = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt1@realdolmen.com");
        User u2 = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt2@realdolmen.com");
        repo.createUser(u1);
        repo.createUser(u2);
        repo.getEntityManager().flush();
    }

    @Test(expected = PersistenceException.class)
    public void emailMustBeUnique1(){
        // database already has a user with email "maarten.dhondt@skynet.be"
        User u = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt@skynet.be");
        repo.createUser(u);
        repo.getEntityManager().flush();
    }

    @Test(expected = PersistenceException.class)
    public void emailMustBeUnique2(){
        User u1 = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt@realdolmen.com");
        User u2 = new User("MDhondt2", "123", UserType.CUSTOMER, "maarten.dhondt@realdolmen.com");
        repo.createUser(u1);
        repo.createUser(u2);
        repo.getEntityManager().flush();
    }

    @Test
    public void usersCanBeRetrieved(){
        assertEquals(1, repo.getAllUsers().size());

        User u1 = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt1@realdolmen.com");
        User u2 = new User("MDhondt2", "123", UserType.CUSTOMER, "maarten.dhondt2@realdolmen.com");
        repo.createUser(u1);
        repo.createUser(u2);
        repo.getEntityManager().flush();
        assertEquals(3, repo.getAllUsers().size());
    }

    @Test
    public void usersCanBeRetrievedByType(){
        assertEquals(1, repo.getAllUsersOfType(UserType.CUSTOMER).size());
        assertEquals(0, repo.getAllUsersOfType(UserType.EMPLOYEE).size());
        assertEquals(0, repo.getAllUsersOfType(UserType.PARTNER).size());

        User u1 = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt1@realdolmen.com");
        User u2 = new User("MDhondt2", "123", UserType.EMPLOYEE, "maarten.dhondt2@realdolmen.com");
        User u3 = new User("MDhondt3", "123", UserType.PARTNER, "maarten.dhondt3@realdolmen.com");
        User u4 = new User("MDhondt4", "123", UserType.CUSTOMER, "maarten.dhondt4@realdolmen.com");
        User u5 = new User("MDhondt5", "123", UserType.EMPLOYEE, "maarten.dhondt5@realdolmen.com");
        repo.createUser(u1); repo.createUser(u2); repo.createUser(u3); repo.createUser(u4); repo.createUser(u5);
        repo.getEntityManager().flush();

        assertEquals(3, repo.getAllUsersOfType(UserType.CUSTOMER).size());
        assertEquals(2, repo.getAllUsersOfType(UserType.EMPLOYEE).size());
        assertEquals(1, repo.getAllUsersOfType(UserType.PARTNER).size());
    }

    @Test
    public void usersCanBeRetrievedById(){
        assertNotNull(repo.getUserById(1));
        User u = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt@realdolmen.com");
        repo.createUser(u); repo.getEntityManager().flush();
        assertEquals(u, repo.getUserById(u.getId()));
    }

    @Test
    public void usersCanBeRetrievedByUsername(){
        assertNotNull(repo.getUserByUsername("MDhondt"));
        User u = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt@realdolmen.com");
        repo.createUser(u); repo.getEntityManager().flush();
        assertEquals(u, repo.getUserByUsername(u.getUsername()));
    }

    @Test
    public void usersCanBeRetrievedByEmail(){
        assertNotNull(repo.getUserByEmail("maarten.dhondt@skynet.be"));
        User u = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt@realdolmen.com");
        repo.createUser(u); repo.getEntityManager().flush();
        assertEquals(u, repo.getUserByEmail(u.getEmail()));
    }


    @Test
    public void hashedPasswordsAreEqualWhenCompared() {
        String unhashedPassword = "Blabla123";
        String hashedPassword = repo.hashPassword(unhashedPassword);
        User user = new User("MDhondt1", unhashedPassword, UserType.CUSTOMER, "maarten.dhondt@realdolmen.com");
        String hashedPasswordCheck = repo.hashPassword(unhashedPassword);
        assertTrue(repo.comparePasswords(user, hashedPasswordCheck));
    }

    @Test
    public void hashedPasswordsAreNotEqualWhenComparedToADifferentPassword() {
        String unhashedPassword = "Blabla123";
        String hashedPassword = repo.hashPassword(unhashedPassword);
        User user = new User("MDhondt1", "Blabla", UserType.CUSTOMER, "maarten.dhondt@realdolmen.com");
        String hashedPasswordCheck = repo.hashPassword(unhashedPassword);
        assertFalse(repo.comparePasswords(user, hashedPasswordCheck));
    }


}
