/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.utils.beans;

import java.io.Serializable;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author DINH
 */
@ManagedBean
@SessionScoped
public class GuestPreferencesBean implements Serializable {

    /** Creates a new instance of GuestPreferencesBean */
    public GuestPreferencesBean() {
    }
    private String theme = "ui-lightness"; //default

    public String getTheme() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params.containsKey("theme")) {
            theme = params.get("theme");
        }
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
