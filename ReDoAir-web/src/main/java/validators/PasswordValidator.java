package validators;

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
    private static final String PASSWORD_REGEX2 = "((?=.*\\d)(?=.*[A-Z])(?=.*\\W).{8,8})";
    private static final String PASSWORD_MUST_HAVE_SPECIAL_CHARACTER = "(?=.*\\W)";
    private static final String PASSWORD_MUST_HAVE_CAPITAL = "(?=.*[A-Z])";
    private static final String PASSWORD_MUST_HAVE_NUMBER = "(?=.*\\d)";

    private Pattern pattern;
    private Pattern capitalPattern;
    private Pattern numberPattern;
    private Pattern specialCharacterPattern;
    private Matcher matcher;

    public PasswordValidator(){
        pattern = Pattern.compile(PASSWORD_REGEX2);
    }
    @Override
    public void validate(FacesContext context, UIComponent component,
                         Object value) throws ValidatorException {
        String password = value.toString();
/*        matcher = pattern.matcher(password);
        if(matcher.matches()) {*/

            UIInput uiInputConfirmPassword = (UIInput) component.getAttributes()
                    .get("confirmPassword");
            String confirmPassword = uiInputConfirmPassword.getSubmittedValue()
                    .toString();

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
        //}
/*        else if(password.length() < 8) {
            throw new ValidatorException(new FacesMessage(
                    "Password must at least be 8 characters long!"));
        }
        else {
            throw new ValidatorException(new FacesMessage(
                    "Password must contain at least one number, capital and special character!"));
        }*/
/*        else if(!(initMatcher(numberPattern, password, PASSWORD_MUST_HAVE_NUMBER).matches())){
            throw new ValidatorException(new FacesMessage(
                    "Password must contain at least one number!"));
        }
        else if(!(initMatcher(capitalPattern, password, PASSWORD_MUST_HAVE_CAPITAL).matches())){
            throw new ValidatorException(new FacesMessage(
                    "Password must contain at least one Capital letter!"));
        }
        else if(password.length() < 8) {
            throw new ValidatorException(new FacesMessage(
                    "Password must at least be 8 characters long!"));
        }
        else if(!(initMatcher(specialCharacterPattern, password, PASSWORD_MUST_HAVE_SPECIAL_CHARACTER).matches())){
            throw new ValidatorException(new FacesMessage(
                    "Password must contain at least one special character(ex:$)!"));
        }*/
    }

    public Matcher initMatcher(Pattern pattern, String value, String password_regex) {
        Pattern curPattern = pattern;
        curPattern = Pattern.compile(password_regex);
        return matcher = curPattern.matcher(value);
    }

}