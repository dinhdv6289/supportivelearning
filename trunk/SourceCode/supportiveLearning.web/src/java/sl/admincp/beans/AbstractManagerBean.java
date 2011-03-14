/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public abstract class AbstractManagerBean<T> implements Serializable {

    /** Creates a new instance of AbstractManagerBean */
    public AbstractManagerBean() {
    }

    public abstract String insert() throws Exception;

    public abstract String update() throws Exception;

    public abstract String delete() throws Exception;

}
