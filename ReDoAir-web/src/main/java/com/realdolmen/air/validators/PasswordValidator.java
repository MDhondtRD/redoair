package com.realdolmen.air.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JDOAX80 on 8/10/2015.
 */
@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator {

    private static final String PASSWORD_REGEX = "^.*(?=.{4,10})(?=.*\\d)(?=.*[a-zA-Z]).*$";
    private static final String PASSWORD_MUST_HAVE_SPECIAL_CHARACTER = "\\W";
    private static final String PASSWORD_MUST_HAVE_AN_UPER_CASE_CHARACTER = "[A-Z]";
    private static final String PASSWORD_MUST_HAVE_A_NUMBER = "\\d";
    private static final String PASSWORD_MUST_HAVE_A_LOWER_CASE_CHARACTER = "[a-z]";
    private static final String PASSWORD_MUSNT_HAVE_A_SPACE_CHARACTER = "^[^ ]*$";

    Pattern [] patternArray = {
            Pattern.compile(PASSWORD_MUST_HAVE_A_LOWER_CASE_CHARACTER),//checks for a lower case character
            Pattern.compile(PASSWORD_MUST_HAVE_AN_UPER_CASE_CHARACTER),//checks for an upper case character
            Pattern.compile(PASSWORD_MUST_HAVE_A_NUMBER),//checks for a number
            Pattern.compile(PASSWORD_MUST_HAVE_SPECIAL_CHARACTER),//checks for a special character
            Pattern.compile(PASSWORD_MUSNT_HAVE_A_SPACE_CHARACTER)//checks for spaces
    };
    int matchCount = 0;

    private Pattern pattern;
    private Pattern capitalPattern;
    private Pattern numberPattern;
    private Pattern specialCharacterPattern;
    private Pattern spaceCharPattern;
    private Matcher matcher;

    public PasswordValidator(){
        pattern = Pattern.compile(PASSWORD_REGEX);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
                         Object value) throws ValidatorException {
        String password = value.toString();
        UIInput uiInputConfirmPassword = (UIInput) component.getAttributes()
                    .get("confirmPassword");
        String confirmPassword = uiInputConfirmPassword.getSubmittedValue()
                    .toString();
        if(password.length() < 8) {
            throw new ValidatorException(new FacesMessage(
                    "Password must at least be 8 characters long!"));
        }
        else if(!matchRegex(patternArray[0], password)){
            throw new ValidatorException(new FacesMessage(
                    "Password must contain at least one lower case character!"));
        }
        else if(!matchRegex(patternArray[1], password)){
            throw new ValidatorException(new FacesMessage(
                    "Password must contain at least one uper case character!"));
        }
        else if(!matchRegex(patternArray[2], password)){
            throw new ValidatorException(new FacesMessage(
                    "Password must contain at least one number!"));
        }
        else if(!matchRegex(patternArray[3], password)){
            throw new ValidatorException(new FacesMessage(
                    "Password must contain at least one special character(ex:$)!"));
        }
        else if(!matchRegex(patternArray[4], password)) {
            throw new ValidatorException(new FacesMessage(
                    "Password must not contain any spaces!"));
        }
        checkForPasswordEquality(password, confirmPassword, uiInputConfirmPassword);
    }

    public void checkForPasswordEquality(String password, String confirmPassword, UIInput uiInputConfirmPassword) {
        // Let required="true" do its job.
        if (password == null || password.isEmpty() || confirmPassword == null
                || confirmPassword.isEmpty()) {
            return;
        }

        if (!password.equals(confirmPassword)) {
            uiInputConfirmPassword.setValid(false);
            throw new ValidatorException(new FacesMessage(
                    "Password must match confirm password."));
        }
    }

    public boolean matchRegex(Pattern pattern, String password) {
        return pattern.matcher(password).find();
    }
}