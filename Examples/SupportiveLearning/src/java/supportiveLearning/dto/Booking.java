package supportiveLearning.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import supportiveLearning.dao.Amenity;

public class Booking implements Serializable {

    private static final long serialVersionUID = 1171567558348174963L;

    private Long id;

    private User user;

    private Hotel hotel;

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    

  //  @NotNull
    private Date checkinDate;

  //  @Future
   // @NotNull
    private Date checkoutDate;

  //  @Pattern(regexp = "[0-9]{16}", message = "is not a 16 digit card number")
    private String creditCard;

 //   @NotEmpty
    private String creditCardName;

    private int creditCardExpiryMonth;

    private int creditCardExpiryYear;

    private boolean smoking;

    private int beds;

    private Amenity[] amenities;

    public Booking() {
    }

    public Booking(Long id, Date checkinDate, Date checkoutDate, String creditCard, String creditCardName, int creditCardExpiryMonth, int creditCardExpiryYear, boolean smoking, int beds, Amenity[] amenities) {
        this.id = id;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.creditCard = creditCard;
        this.creditCardName = creditCardName;
        this.creditCardExpiryMonth = creditCardExpiryMonth;
        this.creditCardExpiryYear = creditCardExpiryYear;
        this.smoking = smoking;
        this.beds = beds;
        this.amenities = amenities;
    }

    

    public Booking(Hotel hotel, User user) {
	this.hotel = hotel;
	this.user = user;
	Calendar calendar = Calendar.getInstance();
	setCheckinDate(calendar.getTime());
	calendar.add(Calendar.DAY_OF_MONTH, 1);
	setCheckoutDate(calendar.getTime());
    }

    public float getTotal() {
	return hotel.getPrice();
    }

    public int getNights() {
	if (checkinDate == null || checkoutDate == null) {
	    return 0;
	} else {
	    return (int) (checkoutDate.getTime() - checkinDate.getTime()) / 1000 / 60 / 60 / 24;
	}
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }
    public Date getCheckinDate() {
	return checkinDate;
    }

    public void setCheckinDate(Date datetime) {
	this.checkinDate = datetime;
    }
    public Hotel getHotel() {
	return hotel;
    }

    public void setHotel(Hotel hotel) {
	this.hotel = hotel;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public Date getCheckoutDate() {
	return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
	this.checkoutDate = checkoutDate;
    }

    public String getCreditCard() {
	return creditCard;
    }

    public void setCreditCard(String creditCard) {
	this.creditCard = creditCard;
    }

    public String getDescription() {
	DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
	return hotel == null ? null : hotel.getName() + ", " + df.format(getCheckinDate()) + " to "
		+ df.format(getCheckoutDate());
    }

    public boolean isSmoking() {
	return smoking;
    }

    public void setSmoking(boolean smoking) {
	this.smoking = smoking;
    }

    public int getBeds() {
	return beds;
    }

    public void setBeds(int beds) {
	this.beds = beds;
    }

    public String getCreditCardName() {
	return creditCardName;
    }

    public void setCreditCardName(String creditCardName) {
	this.creditCardName = creditCardName;
    }

    public int getCreditCardExpiryMonth() {
	return creditCardExpiryMonth;
    }

    public void setCreditCardExpiryMonth(int creditCardExpiryMonth) {
	this.creditCardExpiryMonth = creditCardExpiryMonth;
    }

    public int getCreditCardExpiryYear() {
	return creditCardExpiryYear;
    }

    public void setCreditCardExpiryYear(int creditCardExpiryYear) {
	this.creditCardExpiryYear = creditCardExpiryYear;
    }

    public Amenity[] getAmenities() {
	return amenities;
    }

    public void setAmenities(Amenity[] amenities) {
	this.amenities = amenities;
    }

    private Date today() {
	Calendar calendar = Calendar.getInstance();
	calendar.add(Calendar.DAY_OF_MONTH, -1);
	return calendar.getTime();
    }

    @Override
    public String toString() {
	return "Booking(" + user + "," + hotel + ")";
    }

}
