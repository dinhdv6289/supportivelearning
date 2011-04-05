/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sl.utils.beans;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author TuyenPV
 */
@ManagedBean
@SessionScoped
public class Validation {

    /** Creates a new instance of Validation */
    public Validation() {
    }

    public void validateEmail(FacesContext facesContext,
            UIComponent uIComponent, Object object) throws ValidatorException {

        String enteredEmail = (String) object;
        //Set the email pattern string
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

        //Match the given string with the pattern
        Matcher m = p.matcher(enteredEmail);

        //Check whether match is found
        boolean matchFound = m.matches();
        if (!matchFound) {
            FacesMessage message = new FacesMessage();
            message.setDetail("Email not valid");
            message.setSummary("Email not valid");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

     private String password1;
    private String password2;
    private boolean input1Set;

    public void validateFieldPassword(FacesContext context, UIComponent component, Object value) {
        if (input1Set) {
            password2 = (String) value;
            if (password1 == null || password1.length() < 6 || (!password1.equals(password2))) {
                ((EditableValueHolder) component).setValid(false);
                context.addMessage(component.getClientId(context), new FacesMessage(
                        "Password must be 6 chars & both fields identical"));
            }
        } else {
            input1Set = true;
            password1 = (String) value;
        }
    }
}
