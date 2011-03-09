/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Clazz;
import el.model.Course;
import el.model.Student;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author DINHDV
 */
public class SchoolData {

    private static ArrayList<Course> listCourses = new ArrayList<Course>();
    private static ArrayList<Clazz> listClazzs = new ArrayList<Clazz>();
    private static ArrayList<Student> listStudents = new ArrayList<Student>();

    {
        for (int i = 0; i < 1; i++) {
            Course course = new Course();
            course.setId(i);
            course.setName("Course " + i);
            listCourses.add(course);
        }



        for (int i = 0; i < listCourses.size(); i++) {
            for (int j = 0; j < 10; j++) {
                Clazz classes = new Clazz();
                classes.setId(j);
                classes.setName("Class " + j);
                listClazzs.add(classes);

            }
            listCourses.get(i).setClazzs(listClazzs);

        }



        for (int i = 0; i < listClazzs.size(); i++) {
            for (int j = 0; j < 25; j++) {
                Student student = new Student();
                student.setId(j);
                student.setName("name" + j);
                student.setUserName("userName " + j);
                student.setPassword("password" + j);
                student.setDateCreate(new Date());
                student.setCourse(listCourses.get(0));
                student.setClazz(listClazzs.get(i));
                listStudents.add(student);
            }
        }





    }

    public boolean insert(Student student) {
        if (student != null) {
            if (!isExistStudent(student.getId())) {
                listStudents.add(student);
                return true;
            }
        }
        return false;
    }

    public boolean updateStudent(Student student) {
        if (student != null) {
            for (int i = 0; i < listStudents.size(); i++) {
                if (listStudents.get(i).getId() == student.getId()) {
                    listStudents.get(i).setClazz(student.getClazz());
                    listStudents.get(i).setCourse(student.getCourse());
                    listStudents.get(i).setName(student.getName());
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteStudent(Student student) {
        Student s = getStudentById(student.getId());
        if (s != null) {
            return listStudents.remove(s);
        }
        return false;
    }

    private boolean getStudentInClazz(Student student) {
        for (int i = 0; i < listClazzs.size(); i++) {
            ArrayList<Student> listStudentInClazz = listClazzs.get(i).getStudents();
            for (int j = 0; j < listStudentInClazz.size(); j++) {
                if (listStudentInClazz.get(j).equals(student)) {
                    return listStudentInClazz.remove(student);
                }
            }
        }
        return false;
    }

//    private Student test(ArrayList<Student> listStudentInClazz){
//        for (int i = 0; i < listStudentInClazz.size(); i++) {
//            if(listStudentInClazz.get(i).)
//        }
//    }
    public ArrayList<Student> list() {
        return listStudents;
    }

    private boolean isExistStudent(int id) {
        return this.getStudentById(id) == null ? false : true;
    }

    private Student getStudentById(int id) {
        for (int i = 0; i < listStudents.size(); i++) {
            if (listStudents.get(i).getId() == id) {
                return listStudents.get(i);
            }
        }
        return null;
    }

    private boolean isExistClasses(int id) {
        return this.getClassesById(id) == null ? true : false;
    }

    private Clazz getClassesById(int id) {
        for (int i = 0; i < listClazzs.size(); i++) {
            if (listClazzs.get(i).getId() == id) {
                return listClazzs.get(i);
            }
        }
        return null;
    }

    public boolean insertClazz(Clazz clazz) {
        if (!isExistClasses(clazz.getId())) {
            listClazzs.add(clazz);
            return true;
        }
        return false;
    }

    public boolean updateClazzById(Clazz clazz) {
        if (clazz != null) {
            for (int i = 0; i < listClazzs.size(); i++) {
                if (listClazzs.get(i).getId() == clazz.getId()) {
                    listClazzs.get(i).setName(clazz.getName());
                    listClazzs.get(i).setStudents(clazz.getStudents());
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteClazz(Clazz clazz) {
        Clazz classesResult = getClassesById(clazz.getId());
        if (classesResult != null) {
            return listClazzs.remove(classesResult);
        }
        return false;
    }

    private boolean isExistCourse(int id) {
        return this.getCourseById(id) == null ? true : false;
    }

    private Course getCourseById(int id) {
        for (int i = 0; i < listCourses.size(); i++) {
            if (listCourses.get(i).getId() == id) {
                return listCourses.get(i);
            }
        }
        return null;
    }

    public boolean insertCourse(Course course) {
        if (!isExistCourse(course.getId())) {
            listCourses.add(course);
            return true;
        }
        return false;
    }

    public boolean updateCourseById(Course course) {
        if (course != null) {
            for (int i = 0; i < listCourses.size(); i++) {
                if (listCourses.get(i).getId() == course.getId()) {
                    listCourses.get(i).setName(course.getName());
                    listCourses.get(i).setClazzs(course.getClazzs());
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteCourse(Course course) {
        Course courseResult = getCourseById(course.getId());
        if (courseResult != null) {
            return listCourses.remove(courseResult);
        }
        return false;
    }

    public static ArrayList<Clazz> getListClazzs() {
        return listClazzs;
    }

    public static void setListClazzs(ArrayList<Clazz> listClazzs) {
        SchoolData.listClazzs = listClazzs;
    }

    public static ArrayList<Course> getListCourses() {
        return listCourses;
    }

    public static void setListCourses(ArrayList<Course> listCourses) {
        SchoolData.listCourses = listCourses;
    }

    public static ArrayList<Student> getListStudents() {
        return listStudents;
    }

    public static void setListStudents(ArrayList<Student> listStudents) {
        SchoolData.listStudents = listStudents;
    }
}
