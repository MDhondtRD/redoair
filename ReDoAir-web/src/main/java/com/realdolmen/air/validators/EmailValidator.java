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
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JDOAX80 on 8/10/2015.
 */
@Named
@RequestScoped
public class EmailValidator implements Validator {

    @Inject
    private UserRepository userRepository;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\." +
            "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
            "(\\.[A-Za-z]{2,})$";
    private static final String LOGIN_IN_NAVBAR_VIEW = "loginInNavbar";

    private Pattern pattern;
    private Matcher matcher;

    public EmailValidator(){
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
                         Object value) throws ValidatorException {
        UIInput emailInput = (UIInput) context.getViewRoot().findComponent("form:email");
        String email = value.toString();
        UIInput viewInput = (UIInput) context.getViewRoot().findComponent("form:checkForEmail");
        String view = null;
        if(viewInput != null) {
            view = (String) viewInput.getValue();
        }
        matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            FacesMessage msg =
                    new FacesMessage("E-mail validation failed.",
                            "Invalid E-mail format.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        User user = userRepository.getUserByEmail(email);
        if(user != null && !view.equals(LOGIN_IN_NAVBAR_VIEW)) {
            throw new ValidatorException(new FacesMessage(
                    "There already exists an account for this email address!"));
        }
        else if(user == null && view != null) {
            if(view.equals(LOGIN_IN_NAVBAR_VIEW)) {
                context.getViewRoot().findComponent("form:register").setRendered(true);
                throw new ValidatorException(new FacesMessage(
                        "There doesn't exist an account for the entered email address"));
            }
        }

    }

}
