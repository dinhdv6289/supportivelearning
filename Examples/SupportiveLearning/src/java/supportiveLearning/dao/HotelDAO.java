package supportiveLearning.dao;

import supportiveLearning.Intefaces.BookingService;
import supportiveLearning.dto.User;
import supportiveLearning.dto.Hotel;
import supportiveLearning.dto.Booking;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A JPA-based implementation of the Booking Service. Delegates to a JPA entity manager to issue data access calls
 * against the backing repository. The EntityManager reference is provided by the managing container (Spring)
 * automatically.
 */
public class HotelDAO implements BookingService, Serializable {

    private static final long serialVersionUID = 1L;
    private String searchString = "";

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public List<Booking> findBookings(String username) {
        if (username != null) {
            return null;
        } else {
            return null;
        }
    }
    private HotelData hD = new HotelData();

    public List<Hotel> findHotels() {
        return hD.getAllHotels();
    }

    public Hotel findHotelById(Long id) {
        ArrayList<Hotel> list = hD.getAllHotels();
        if (id != null) {
            for (int i = 0; i < list.size(); i++) {
                Hotel h = list.get(i);
                if (h.getId().equals(id)) {
                    return h;
                }
            }
        }
        return null;
    }

    public Booking createBooking(Long hotelId, String username) {
	Hotel hotel = findHotelById(hotelId);
	User user = findUser(username);
	Booking booking = new Booking(hotel, user);
	new BookinglData().add(booking);
	return booking;
    }

    // read-write transactional methods
    public void cancelBooking(Booking booking) {
        //booking = em.find(Booking.class, booking.getId());
        if (booking != null) {
            //   em.remove(booking);
        }
    }

    private User findUser(String username) {
//	return (User) em.createQuery("select u from User u where u.username = :username").setParameter("username",
//		username).getSingleResult();
        return null;
    }
}
