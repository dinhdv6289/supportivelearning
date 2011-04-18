/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.utils.beans;

import java.io.Serializable;
import java.util.Locale;
import java.util.TimeZone;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author TuyenPV
 */
@ManagedBean
@SessionScoped
public class ConverterBean extends UtilCheckLoginBean implements Serializable {

    /** Creates a new instance of ConverterBean */
    public ConverterBean() {
        super();
    }
    private String strLocale;
    private TimeZone timezone;

    public TimeZone getTimezone() {
        this.timezone = TimeZone.getDefault();
        return timezone;
    }

    public String getStrLocale() {
        this.strLocale = Locale.getDefault().toString();

        return strLocale;
    }
}
