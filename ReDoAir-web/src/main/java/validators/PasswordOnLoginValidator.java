package validators;

import com.realdolmen.redoair.ejb.UserRepository;
import com.realdolmen.redoair.entities.User;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

/**
 * Created by JDOAX80 on 8/10/2015.
 */
@FacesValidator("passwordOnLoginValidator")
public class PasswordOnLoginValidator implements Validator {

    @Inject
    private UserRepository userRepository;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object user) throws ValidatorException {
        if(user != null) {
            User userToCheck = (User) user;
            if(!userRepository.validateUser(userToCheck)) {
                FacesMessage msg =
                        new FacesMessage("Invalid user credentials",
                                "Invalid E-mail format.");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

}
