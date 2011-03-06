/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package supportiveLearning.dao;

import java.io.Serializable;
import java.util.ArrayList;
import supportiveLearning.dto.Hotel;
import supportiveLearning.dto.User;

/**
 *
 * @author DINHDV
 */
public class UserData implements Serializable {

    private ArrayList<User> users ;

    public UserData() {
        users = new ArrayList<User>();
    }

    public ArrayList<User> getAllUsers() {
        return users;

    }


    public void add(User user){
        users.add(user);
    }
}
