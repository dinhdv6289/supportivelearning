/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.utils.beans;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DINH
 */
public class LoginService {

    public static void loginService(String uri) {
        try {
            if (uri.equals("") || uri == null) {
                HttpServletService.getHttpServletResponse().sendRedirect("login.jsf");
            } else {
                HttpServletService.getHttpServletResponse().sendRedirect("login.jsf?pageRq=" + uri);
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
