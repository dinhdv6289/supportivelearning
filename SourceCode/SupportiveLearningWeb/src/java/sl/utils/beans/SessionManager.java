/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.utils.beans;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DINHDV
 */
public class SessionManager {

    public SessionManager() {
    }

    public static Object getSession(String key) {
        return getHttpSession().getAttribute(key);
    }

    public static void setSession(String key, Object value) {
        getHttpSession().setAttribute(key, value);
    }

    public static void invalidate(String key) {
        getHttpSession().invalidate();
    }

    private static HttpSession getHttpSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }


}
