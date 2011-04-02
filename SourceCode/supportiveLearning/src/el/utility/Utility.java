/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.utility;

import java.util.Date;

/**
 *
 * @author TuyenPV
 */
public class Utility {

    public static String GenerateUserName(String str) {
       String[] s = str.split(" ");
        String b = "";
        String name = s[s.length - 1];
        for (int i = 0; i < s.length; i++) {
            if (i < s.length - 1) {
                b += s[i].substring(0, 1);
            }
        }
        String result = name + b;
        return result.toLowerCase();
    }

    public static Date sql2date(java.sql.Date date) {
        if (date == null) {
            return null;
        }
        return new java.util.Date(date.getTime());
    }

    public static java.sql.Date date2sql(Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }
 
}
