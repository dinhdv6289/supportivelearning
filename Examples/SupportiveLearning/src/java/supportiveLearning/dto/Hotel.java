package supportiveLearning.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A hotel where users may book stays.
 */
public class Hotel implements Serializable {

    private static final long serialVersionUID = 4011346719502656269L;

    private Long id;

    private String name;

    private String address;

    private String city;

    private String state;

    private String zip;

    private String country;

    private float price;

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getZip() {
	return zip;
    }

    public void setZip(String zip) {
	this.zip = zip;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    public float getPrice() {
	return price;
    }

    public void setPrice(float price) {
	this.price = price;
    }

    public Booking createBooking(User user) {
	return new Booking(this, user);
    }

    @Override
    public String toString() {
	return "Hotel(" + name + "," + address + "," + city + "," + zip + ")";
    }

    public Hotel(Long id, String name, String address, String city, String state, String zip, String country, float price) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.price = price;
    }

    
}
