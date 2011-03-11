/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package el.dao;

import el.model.Student;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author TuyenPV
 */
public class StudentDAOTest {

    public StudentDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testList() throws Exception {
        System.out.println("list");
        StudentDAO instance = new StudentDAO();
        ArrayList<Student> result = instance.list();
        for (Student student : result) {
            System.out.println(student.getClazz().getName());
        }

    }


}