/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sl.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public abstract class AbstractBean<T> implements Serializable{

    /** Creates a new instance of AbstractBean */
    public AbstractBean() {
    }

    public abstract String insert(T t) throws Exception;
    public abstract String update(T t) throws Exception;
    public abstract String delete(T t) throws Exception;

    private int id;
    private String name;
    private String userName;
    


}
