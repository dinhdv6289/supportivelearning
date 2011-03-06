/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package supportiveLearning.dao;

import java.io.Serializable;
import java.util.ArrayList;
import supportiveLearning.dto.Hotel;

/**
 *
 * @author DINHDV
 */
public class HotelData implements Serializable {

    private ArrayList<Hotel> hotels ;

    public HotelData() {
        hotels = new ArrayList<Hotel>();
    }

    public ArrayList<Hotel> getAllHotels() {
        hotels.add(new Hotel(1L, "Westin Diplomat", "35đ55 S. Oceadddn Drive", "Hollywddood", "FLđ", "3301đ9", "ddUSA", 4432));
        hotels.add(new Hotel(2L, "Westin Jameson", "3555 S. Ocean Drive", "Hollywood", "FL", "33019", "UddSA", 44));
        hotels.add(new Hotel(3L, "Chilworth Diplomat", "3555 S. Ocean Drive", "Hollywood", "FL", "33019", "ffffffUSA", 33));
        hotels.add(new Hotel(4L, "Marriott Diplomat", "3555 S. Ocean Drive", "Hollywood", "FL", "33019", "USAfff", 454));
        return hotels;

    }


    public void add(){

    }
}
