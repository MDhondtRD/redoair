package com.realdolmen.air;

import com.realdolmen.air.controllers.SignUpController;
import com.realdolmen.redoair.ejb.UserRepository;
import com.realdolmen.redoair.entities.Address;
import com.realdolmen.redoair.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

import static org.mockito.Matchers.any;

/**
 * Created by JDOAX80 on 12/10/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class SignUpControllerTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SignUpController signUpController;

    @Test
    public void createUserCanBeCalledOnSignUpController() {
        User user =  new User("Username", "123azerty", "bla@bla.com");
        Address address = new Address("Bla", "Bla", "BlaCity", "44512", "Belgium");
        user.setAddress(address);
        signUpController.setUser(user);
        signUpController.createUser();
        Mockito.verify(userRepository).createUser(user);
    }
}
