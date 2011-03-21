/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.utils.beans;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DINHDV
 */
public class HttpServletService {

    public static HttpServletResponse getHttpServletResponse() {
        FacesContext context = FacesContext.getCurrentInstance();

        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        return response;
    }

    public static String getRequestContextPath() {
        String requestContextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        return requestContextPath;
    }
}
