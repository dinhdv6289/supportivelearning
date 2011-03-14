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
public abstract class AbstractBean<T> implements Serializable {

    protected Class<T> currentClass;

    /** Creates a new instance of AbstractBean */
    public AbstractBean() {
    }

    public Class<T> getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(Class<T> currentClass) {
        this.currentClass = currentClass;
    }

    public String insert() throws Exception {

        return null;
    }

    public String update() throws Exception {
        return null;
    }

    public String delete() throws Exception {
        return null;
    }

    public String login() throws Exception {
        return null;
    }
}
