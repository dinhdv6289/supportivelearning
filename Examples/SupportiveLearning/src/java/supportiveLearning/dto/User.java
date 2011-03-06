package supportiveLearning.dto;

import java.io.Serializable;


/**
 * A user who can book hotels.
 */

public class User implements Serializable {

    private static final long serialVersionUID = -3652559447682574722L;

    private String username;

    private String password;

    private String name;

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    

    public User() {
    }

    public User(String username, String password, String name) {
	this.username = username;
	this.password = password;
	this.name = name;
    }


    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public String toString() {
	return "User(" + username + ")";
    }
}
