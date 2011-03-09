/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package el.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@RequestScoped
public class TestBean {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String redirect(){
        return "new";
    }

}
