/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.CourseDAO;
import el.model.Course;
import el.model.Staff;
import el.model.Student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sl.utils.beans.EachSession;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class CourseBean implements Serializable {

    private CourseDAO courseDAO = new CourseDAO();
    private ArrayList<Course> listCourses = new ArrayList<Course>();

    /** Creates a new instance of CourseBean */
    public CourseBean() {
        loadCourses();
    }

    public ArrayList<Course> getListCourses() {
        return listCourses;
    }

    public void setListCourses(ArrayList<Course> listCourses) {
        this.listCourses = listCourses;
    }

    private void loadCourses() {
        try {
            Object object = EachSession.getObjectFromSession("accountId");
            if (object != null) {
                listCourses = this.loadListCoursesByObject(object);
            }
        } catch (Exception ex) {
            Logger.getLogger(CourseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ArrayList<Course> loadListCoursesByObject(Object object) {
        ArrayList<Course> courses = new ArrayList<Course>();
        try {
            if (object != null) {
                if (object instanceof Student) {
                    Student student = (Student) object;
                    //courses = courseDAO.getListCoursesBy(student);
                } else if (object instanceof Staff) {
                    Staff staff = (Staff) object;
                    //courses = courseDAO.getListCoursesBy(staff);
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(StudentBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return courses;
    }
}
