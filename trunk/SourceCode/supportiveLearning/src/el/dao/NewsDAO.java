/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package el.dao;

import el.model.News;
import java.util.ArrayList;

/**
 *
 * @author DINHDV
 */
public class NewsDAO extends AbstractDAO<News> {

    @Override
    public int insert(News t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(News t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(News t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<News> list() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<News> listNewsByCategoryId(int categoryId) throws Exception{
        return null;
    }

    @Override
    public News getObject(News t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
