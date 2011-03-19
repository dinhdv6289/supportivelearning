/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package el.model;

/**
 *
 * @author DINHDV
 */
public class News {

    private int id;
    private String title;
    private String image;
    private String descriptions;

    public News() {
    }

    public News(int id) {
        this.id = id;
    }

    public News(int id, String title) {
        this(id);
        this.title = title;
    }

    public News(int id, String title, String image, String descriptions) {
        this(id,title);
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
