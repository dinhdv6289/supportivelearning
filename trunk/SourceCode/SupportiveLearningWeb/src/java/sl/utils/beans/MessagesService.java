/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.utils.beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author DINH
 */
public class MessagesService {

    public static void showMessage(String messages) {
        FacesMessage error = new FacesMessage(messages);
        FacesContext.getCurrentInstance().addMessage(null, error);
    }
}
