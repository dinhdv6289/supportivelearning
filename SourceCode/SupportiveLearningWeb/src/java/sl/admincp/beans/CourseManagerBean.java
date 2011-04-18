/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import el.dao.CourseDAO;
import el.model.Course;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sl.utils.beans.UtilCheckLoginBean;

/**
 *
 * @author DINH
 */
@ManagedBean
@SessionScoped
public class CourseManagerBean extends UtilCheckLoginBean implements Serializable {

    private Course course;
    private Course selectedCourse;
    private ArrayList<Course> listCourses = new ArrayList<Course>();
    private CourseDAO courseDAO = new CourseDAO();

    /** Creates a new instance of CourseManagerBean */
    public CourseManagerBean() {
        super();
    }

    @PostConstruct
    public void init() {
        getListCourses();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public ArrayList<Course> getListCourses() {
        try {
            if (listCourses.isEmpty()) {
                listCourses = courseDAO.list();
            }
            return listCourses;
        } catch (Exception ex) {
            Logger.getLogger(CourseManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void setListCourses(ArrayList<Course> listCourses) {
        this.listCourses = listCourses;
    }

    public Course getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }
}
