/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import el.model.FAQ;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class FAQManagerBean extends AbstractManagerBean<FAQ> implements Serializable {

    /** Creates a new instance of FAQManagerBean */
    public FAQManagerBean() {
    }

    @Override
    public String insert() {
        //trycatch
        return null;
    }

    @Override
    public String update() {
        //trycatch
        return null;
    }

    @Override
    public String delete() {

        return null;
    }



}
