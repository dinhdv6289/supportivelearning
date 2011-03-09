/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package el.dao;

import el.model.Student;
import java.util.ArrayList;

/**
 *
 * @author DINHDV
 */
public class StudentDAO extends AbstractDAO<Student> {

    private SchoolData schoolData = new SchoolData();

    public StudentDAO() {
        SchoolData.getListStudents();
    }

    @Override
    public boolean insert(Student student) {
        return schoolData.insert(student);
    }

    @Override
    public boolean update(Student student) {
        return schoolData.updateStudent(student);
    }

    @Override
    public boolean delete(Student student) {

        return schoolData.deleteStudent(student);
    }
    


    @Override
    public ArrayList<Student> list() {
        return SchoolData.getListStudents();
    }




    public static void main(String[] args) {
        StudentDAO studentDAO = new StudentDAO();
        ArrayList<Student> list = studentDAO.list();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                System.out.println("course:" + list.get(i).getCourse().getName());
            }

        }
    }
}
