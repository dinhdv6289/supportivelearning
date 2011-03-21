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

    public Account(int id) {
        this.id = id;
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

    public Account setRole(Role role) {
        this.role = role;
        return this;
    }

    public int getId() {
        return id;
    }

    public Account setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Account setName(String name) {
        this.name = name;
        return this;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public Account setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Account setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Account setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Account setAddress(String address) {
        this.address = address;
        return this;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public Account setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Account setEmail(String email) {
        this.email = email;
        return this;
    }

    public Boolean getGender() {
        return gender;
    }

    public Account setGender(Boolean gender) {
        this.gender = gender;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Account setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
