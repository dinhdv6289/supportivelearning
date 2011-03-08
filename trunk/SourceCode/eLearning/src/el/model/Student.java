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

    private Clazz clazzs;
    private Course course;

    public Clazz getClazzs() {
        return clazzs;
    }

    public void setClazzs(Clazz clazzs) {
        this.clazzs = clazzs;
    }
    


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    


}
