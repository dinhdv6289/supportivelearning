/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import java.util.ArrayList;

/**
 *
 * @author DINHDV
 */
public abstract class AbstractDAO<T> {

    public abstract boolean insert(T t);

    public abstract boolean update(T t);

    public abstract boolean delete(T t);

    public abstract ArrayList<T> list();
}
