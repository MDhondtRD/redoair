package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.ejb.UserRepository;
import com.realdolmen.redoair.entities.Address;
import com.realdolmen.redoair.entities.User;
import com.realdolmen.redoair.entities.UserType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.PersistenceException;
import java.util.List;

public class UserRepositoryTest extends DataPersistenceTest {

    private UserRepository repo;
    private static boolean populated;
    private int numberOfCustomers = 50;
    private int numberOfEmployees = 5;
    private int numberOfPartners = 20;
    private int numberOfUsers = numberOfCustomers + numberOfEmployees + numberOfPartners;

    @Before
    public void before() {
        initialiseRepositories();
        if (!populated) {
            populateDatabase();
            populated = true;
        }
    }

    @After
    public void after(){
        for (User u : repo.getAllUsers())
            repo.removeUser(u);
        repo.getEntityManager().flush();
        populated = false;
    }

    public void initialiseRepositories() {
        repo = new UserRepository();
        repo.setEntityManager(entityManager());
    }

    public void populateDatabase() {
        for (int i = 1; i <= numberOfCustomers; i++)
            repo.createUser(new User("Customer " + (1000 + i), "123", UserType.CUSTOMER, "maarten.customer" + i + "@skynet.be"));
        for (int i = 1; i <= numberOfEmployees; i++)
            repo.createUser(new User("Employee " + (1000 + i), "123", UserType.EMPLOYEE, "maarten.employee" + i + "@skynet.be"));
        for (int i = 1; i <= numberOfPartners; i++)
            repo.createUser(new User("Partner " + (1000 + i), "123", UserType.PARTNER, "maarten.partner" + i + "@skynet.be"));
        repo.getEntityManager().flush();
    }

    @Test
    public void allUsersCanBeRetrieved() {
        assertEquals(numberOfUsers, repo.getAllUsers().size());
    }

    @Test
    public void allUsersCanBeRetrievedByType() {
        assertEquals(numberOfCustomers, repo.getAllUsersOfType(UserType.CUSTOMER).size());
        assertEquals(numberOfPartners, repo.getAllUsersOfType(UserType.PARTNER).size());
        assertEquals(numberOfEmployees, repo.getAllUsersOfType(UserType.EMPLOYEE).size());
    }

    @Test
    public void usersCanBeRetrievedById() {
        int startIndex = repo.getEntityManager().createQuery("SELECT MIN(u.id) FROM User u", Integer.class).getSingleResult();
        for (int i = startIndex; i <= numberOfUsers; i++)
            assertNotNull(repo.getUserById(i));
        assertNull(repo.getUserById(numberOfUsers + 1));
    }

    @Test
    public void usersCanBeRetrievedByUsername() {
        for (int i = 1; i <= numberOfCustomers; i++)
            assertNotNull(repo.getUserByUsername("Customer " + (1000 + i)));
        assertNull(repo.getUserByUsername("Customer " + (1000 + numberOfCustomers + 1)));
        for (int i = 1; i <= numberOfEmployees; i++)
            assertNotNull(repo.getUserByUsername("Employee " + (1000 + i)));
        assertNull(repo.getUserByUsername("EMployee " + (1000 + numberOfEmployees + 1)));
        for (int i = 1; i <= numberOfPartners; i++)
            assertNotNull(repo.getUserByUsername("Partner " + (1000 + i)));
        assertNull(repo.getUserByUsername("Partner " + (1000 + numberOfPartners + 1)));
    }

    @Test
    public void usersCanBeRetrievedByEmail() {
        for (int i = 1; i <= numberOfCustomers; i++)
            assertNotNull(repo.getUserByEmail("maarten.customer" + i + "@skynet.be"));
        assertNull(repo.getUserByEmail("maarten.customer" + (numberOfCustomers + 1) + "@skynet.be"));
        for (int i = 1; i <= numberOfEmployees; i++)
            assertNotNull(repo.getUserByEmail("maarten.employee" + i + "@skynet.be"));
        assertNull(repo.getUserByEmail("maarten.employee" + (numberOfEmployees + 1) + "@skynet.be"));
        for (int i = 1; i <= numberOfPartners; i++)
            assertNotNull(repo.getUserByEmail("maarten.partner" + i + "@skynet.be"));
        assertNull(repo.getUserByEmail("maarten.partner" + (numberOfPartners + 1) + "@skynet.be"));
    }

    @Test
    public void usersCanBeCreatedAndRemoved() {
        assertEquals(numberOfUsers, repo.getAllUsers().size());
        assertEquals(numberOfCustomers, repo.getAllUsersOfType(UserType.CUSTOMER).size());
        assertEquals(numberOfEmployees, repo.getAllUsersOfType(UserType.EMPLOYEE).size());
        assertEquals(numberOfPartners, repo.getAllUsersOfType(UserType.PARTNER).size());
        User customer = new User("Customer", "123", UserType.CUSTOMER, "maarten.customer@skynet.be");
        User employee = new User("Employee", "123", UserType.EMPLOYEE, "maarten.employee@skynet.be");
        User partner = new User("Partner", "123", UserType.PARTNER, "maarten.partner@skynet.be");
        repo.createUser(customer);
        repo.createUser(employee);
        repo.createUser(partner);
        repo.getEntityManager().flush();
        assertEquals(numberOfUsers + 3, repo.getAllUsers().size());
        assertEquals(numberOfCustomers + 1, repo.getAllUsersOfType(UserType.CUSTOMER).size());
        assertEquals(numberOfEmployees + 1, repo.getAllUsersOfType(UserType.EMPLOYEE).size());
        assertEquals(numberOfPartners + 1, repo.getAllUsersOfType(UserType.PARTNER).size());
        repo.removeUser(customer);
        repo.removeUser(employee);
        repo.removeUser(partner);
        repo.getEntityManager().flush();
        assertEquals(numberOfUsers, repo.getAllUsers().size());
        assertEquals(numberOfCustomers, repo.getAllUsersOfType(UserType.CUSTOMER).size());
        assertEquals(numberOfEmployees, repo.getAllUsersOfType(UserType.EMPLOYEE).size());
        assertEquals(numberOfPartners, repo.getAllUsersOfType(UserType.PARTNER).size());
    }


}
