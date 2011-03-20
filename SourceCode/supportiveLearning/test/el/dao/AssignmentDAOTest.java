/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package el.dao;

import el.model.Assignment;
import el.model.Staff;
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
public class AssignmentDAOTest {

    public AssignmentDAOTest() {
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

    /**
     * Test of insert method, of class AssignmentDAO.
     */
//    @Test
//    public void testInsert() throws Exception {
//        System.out.println("insert");
//        Assignment t = null;
//        AssignmentDAO instance = new AssignmentDAO();
//        int expResult = 0;
//        int result = instance.insert(t);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of update method, of class AssignmentDAO.
//     */
//    @Test
//    public void testUpdate() throws Exception {
//        System.out.println("update");
//        Assignment t = null;
//        AssignmentDAO instance = new AssignmentDAO();
//        boolean expResult = false;
//        boolean result = instance.update(t);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of delete method, of class AssignmentDAO.
//     */
//    @Test
//    public void testDelete() throws Exception {
//        System.out.println("delete");
//        Assignment t = null;
//        AssignmentDAO instance = new AssignmentDAO();
//        boolean expResult = false;
//        boolean result = instance.delete(t);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

//    /**
//     * Test of list method, of class AssignmentDAO.
//     */
    @Test
    public void testList() throws Exception {
        System.out.println("list");
        AssignmentDAO instance = new AssignmentDAO();
        ArrayList<Assignment> result = instance.list();
        for (Assignment assignment : result) {
            System.out.println(assignment.getName());
        }
        
    }
//
//    /**
//     * Test of getObject method, of class AssignmentDAO.
//     */
//    @Test
//    public void testGetObject() throws Exception {
//        System.out.println("getObject");
//        Assignment t = null;
//        AssignmentDAO instance = new AssignmentDAO();
//        Assignment expResult = null;
//        Assignment result = instance.getObject(t);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getListAssignmentsByStaff method, of class AssignmentDAO.
//     */
//   @Test
//    public void testGetListAssignmentsByBatchId() throws Exception {
//        System.out.println("getListAssignmentsByStaff");
//        AssignmentDAO instance = new AssignmentDAO();
//        ArrayList<Assignment> result = instance.getListAssignmentsByBatchId(1);
//        for (Assignment assignment : result) {
//            System.out.println(assignment.getName());
//        }
//
//    }


}