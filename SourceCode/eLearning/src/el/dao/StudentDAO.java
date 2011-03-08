/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Clazz;
import el.model.Course;
import el.model.Student;
import java.util.ArrayList;

/**
 *
 * @author DINHDV
 */
public class StudentDAO extends AbstractDAO<Student> {

    // thu bien ko phai toan cuc.
    private  ArrayList<Student> students = new ArrayList<Student>();

    public StudentDAO() {
        createTemplateDatas();
    }

    private ArrayList<Clazz> createClazzs() {
        ArrayList<Clazz> clazzs = new ArrayList<Clazz>();
        for (int i = 0; i < 10; i++) {
            Clazz clazz = new Clazz();
            clazz.setId(i);
            clazz.setName("class Name:" + i);
            clazzs.add(clazz);
        }
        return clazzs;

    }

    private void createTemplateDatas() {
        Course course = new Course(1, "ABC", createClazzs());
        ArrayList<Clazz> listClazzs = course.getClazzs();
        for (int i = 0; i < listClazzs.size(); i++) {
            for (int j = 0; j < 25; j++) {
                Student student = new Student();
                student.setId(j);
                student.setName("name" + j);
                student.setCourse(course);
                student.setClazzs(listClazzs.get(i));
                students.add(student);
            }
        }
    }

    @Override
    public boolean insert(Student student) {
        if (student != null) {
            if (!checkExistStudent(student.getId())) {
                students.add(student);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean update(Student student) {
        if (student != null) {
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getId() == student.getId()) {
                    students.get(i).setClazzs(student.getClazzs());
                    students.get(i).setCourse(student.getCourse());
                    students.get(i).setName(student.getName());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean delete(Student student) {
        Student s = getStudentById(student.getId());
        if (s != null) {
            return students.remove(s);
        }
        return false;
    }

    @Override
    public ArrayList<Student> list() {
        return students;
    }

    private boolean checkExistStudent(int id) {
        boolean flag = false;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) {
                flag = true;
            }
            flag = false;
        }
        return flag;
    }

    private Student getStudentById(int id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) {
                return students.get(i);
            }
        }
        return null;
    }
}
