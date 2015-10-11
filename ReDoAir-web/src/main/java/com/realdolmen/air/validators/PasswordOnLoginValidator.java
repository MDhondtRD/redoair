package com.realdolmen.air.validators;

import com.realdolmen.redoair.ejb.UserRepository;
import com.realdolmen.redoair.entities.User;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by JDOAX80 on 8/10/2015.
 */
@Named
@RequestScoped
public class PasswordOnLoginValidator implements Validator {

    @Inject
    private UserRepository userRepository;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        UIInput emailInput = (UIInput) facesContext.getViewRoot().findComponent("form:email");
        UIInput passwordInput = (UIInput) facesContext.getViewRoot().findComponent("form:password");
        String email = (String) emailInput.getValue();
        String password = (String) passwordInput.getValue();
        System.out.println("EMAIL: " + email + " AND PASSWORD: " + password);
        User userToCheck = new User();
        userToCheck.setEmail(email);
        userToCheck.setPassword(password);
        if (!userRepository.validateUser(userToCheck)) {
            throw new ValidatorException(new FacesMessage(
                    "Invalid user credentials!"));
        }
    }

}
