/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.model;

import java.io.Serializable;

/**
 *
 * @author DINHDV
 */
public class NewsCategory implements Serializable {

    private int id;
    private String newsCategoryName, descriptions;

    public NewsCategory() {
    }

    public NewsCategory(int id) {
        this.id = id;
    }

    public NewsCategory(int id, String newsCategoryName, String descriptions) {
        this.id = id;
        this.newsCategoryName = newsCategoryName;
        this.descriptions = descriptions;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewsCategoryName() {
        return newsCategoryName;
    }

    public void setNewsCategoryName(String newsCategoryName) {
        this.newsCategoryName = newsCategoryName;
    }
}
