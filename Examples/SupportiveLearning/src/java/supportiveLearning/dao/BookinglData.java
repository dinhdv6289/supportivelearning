/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package supportiveLearning.dao;

import java.io.Serializable;
import java.util.ArrayList;
import supportiveLearning.dto.Booking;

/**
 *
 * @author DINHDV
 */
public class BookinglData implements Serializable {

    private ArrayList<Booking> books ;

    public BookinglData() {
        books = new ArrayList<Booking>();
    }

    public ArrayList<Booking> getAllBooks() {
        return books;

    }


    public void add(Booking booking){
        books.add(booking);
    }
}
