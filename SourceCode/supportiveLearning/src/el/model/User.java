/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.model;

import java.util.Date;

/**
 *
 * @author DINHDV
 */
public class User {

    private int id;
    private String name;
    private String userName;
    private String password;
    private Date dateCreate;

    public User() {
    }

    public User(String name, String userName, String password, Date dateCreate) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.dateCreate = dateCreate;
    }

    public User(int id, String name, String userName, String password, Date dateCreate) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.dateCreate = dateCreate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
