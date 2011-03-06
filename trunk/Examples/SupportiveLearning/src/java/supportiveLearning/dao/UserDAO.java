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
public class UserDAO implements  Serializable {

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
    private UserData userDAta = new UserData();

    public List<User> findUsers() {
        return userDAta.getAllUsers();
    }

    public User findHotelById(String name) {
        ArrayList<User> list = userDAta.getAllUsers();
        if (name != null) {
            for (int i = 0; i < list.size(); i++) {
                User h = list.get(i);
                if (h.getUsername().equals(name)) {
                    return h;
                }
            }
        }
        return null;
    }

    public void createUser(User user) {
	userDAta.add(user);
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
