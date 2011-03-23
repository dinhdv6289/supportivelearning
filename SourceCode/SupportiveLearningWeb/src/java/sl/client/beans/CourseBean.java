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
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import sl.utils.beans.EachSession;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class CourseBean implements Serializable {

    private Course course;
    private CourseDAO courseDAO = new CourseDAO();
    private ArrayList<Course> listCourses = new ArrayList<Course>();
    //   private TreeNode root;

    /** Creates a new instance of CourseBean */
    public CourseBean() {
        //    root = new DefaultTreeNode("Root", null);
        //  addToTreeNode(root);
       // loadCourses();
    }

    public ArrayList<Course> getListCourses() {
        return listCourses;
    }

    public void setListCourses(ArrayList<Course> listCourses) {
        this.listCourses = listCourses;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String onRequestCourse(Course course) {
        this.setCourse(course);
        return "courseDetails";
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

//    private void addToTreeNode(TreeNode root) {
//        try {
//            Object object = EachSession.getObjectFromSession("accountId");
//            ArrayList<Course> loadListCoursesByObject = this.loadListCoursesByObject(object);
//            for (int i = 0; i < loadListCoursesByObject.size(); i++) {
//                Course course = loadListCoursesByObject.get(i);
//                new DefaultTreeNode(course.getName(), root);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(CourseBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    public TreeNode getRoot() {
//        return root;
//    }
//
//    public void setRoot(TreeNode root) {
//        this.root = root;
//    }
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
