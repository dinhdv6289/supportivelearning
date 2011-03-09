/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package el.model;

/**
 *
 * @author DINHDV
 */
public class Student extends User{

    private Clazz clazz;
    private Course course;

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }
    


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    


}
