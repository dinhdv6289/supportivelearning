/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public abstract class AbstractBean implements Serializable {

    /** Creates a new instance of AbstractBean */
    public AbstractBean() {
    }

    public abstract String insert(ActionEvent event) throws Exception;

    public abstract String update(ActionEvent event) throws Exception;

    public abstract String delete(ActionEvent event) throws Exception;

    public abstract String login(ActionEvent event) throws Exception;
}
