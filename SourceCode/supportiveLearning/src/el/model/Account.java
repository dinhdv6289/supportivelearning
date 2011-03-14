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
public class Account {

    private int id;
    private String name;
    private String userName;
    private String password;
    private Date dateCreate;
    private Role role;
    private Date birthDay;
    private Boolean gender;
    private String phone;
    private String email;
    private String address;

    public Account() {
    }

    public Account(String name, String userName, String password, Date dateCreate, Role role, Date birthDay, Boolean gender, String phone, String email, String address) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.dateCreate = dateCreate;
        this.role = role;
        this.birthDay = birthDay;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Account(int id, String name, String userName, String password, Date dateCreate, Role role, Date birthDay, Boolean gender, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.dateCreate = dateCreate;
        this.role = role;
        this.birthDay = birthDay;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
