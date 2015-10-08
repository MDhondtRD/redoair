package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.entities.User;
import com.realdolmen.redoair.entities.UserType;
import org.junit.Test;

import javax.persistence.PersistenceException;

/**
 * Created by JDOAX80 on 8/10/2015.
 */
public class UserPersistenceTest extends DataPersistenceTest {

    @Test
    public void userCanBeCreated() {
        User user = new User("Joske", "Blabla","Joske@gmail.com");
        entityManager().persist(user);
        assertNotNull(user.getId());
    }

    @Test(expected = PersistenceException.class)
    public void userNameMustBeUnique(){
        User u1 = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt1@realdolmen.com");
        User u2 = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt2@realdolmen.com");
        entityManager().persist(u1);
        entityManager().persist(u2);
    }

    @Test(expected = PersistenceException.class)
    public void emailMustBeUnique(){
        User u1 = new User("MDhondt1", "123", UserType.CUSTOMER, "maarten.dhondt@realdolmen.com");
        User u2 = new User("MDhondt2", "123", UserType.CUSTOMER, "maarten.dhondt@realdolmen.com");
        entityManager().persist(u1);
        entityManager().persist(u2);
    }

    //TO DO: TEST on the non nullable fields
}
