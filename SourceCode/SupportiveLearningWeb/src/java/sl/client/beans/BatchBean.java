/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.BatchDAO;
import el.model.Batch;
import el.model.Course;
import el.model.Role;
import el.model.Semester;
import el.model.Student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class BatchBean implements Serializable {

    private Batch batch = new Batch();
    private ArrayList<Batch> listBatchs;
    private BatchDAO batchDAO = new BatchDAO();
    private Batch batchDetails;

    /** Creates a new instance of BatchBean */
    public BatchBean() {
    }

    public Batch getBatchDetails() {
        return batchDetails;
    }

    public void setBatchDetails(Batch batchDetails) {
        this.batchDetails = batchDetails;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public ArrayList<Batch> getListBatchs() {
        return listBatchs;
    }

    public void setListBatchs(ArrayList<Batch> listBatchs) {
        this.listBatchs = listBatchs;
    }

    public String loadListBatchs(int id) {
        try {
            this.listBatchs = list();
        } catch (Exception ex) {
            Logger.getLogger(BatchBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "semesterDetails";
    }

    public String loadList() {
        this.listBatchs = list();
        return "semesterDetails.jsf?faces-redirect=true";
    }
//    public String loadListBatchs(int id) {
//        try {
//            this.listBatchs = batchDAO.listBatchOfSemester(id);
//        } catch (Exception ex) {
//            Logger.getLogger(BatchBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return "semesterDetails";
//    }

    private void getBatch(Batch batch) {
        try {
            this.batchDetails = batchDAO.getObject(batch);
        } catch (Exception ex) {
            Logger.getLogger(BatchBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ArrayList<Batch> list() {
        ArrayList<Batch> list = new ArrayList<Batch>();
        ArrayList<Student> listStudent = new ArrayList<Student>();

        for (int i = 1; i <= 25; i++) {
            Student s = new Student();
            s.setAddress("hn");
            s.setBirthDay(new Date());
            s.setDateCreate(new Date());
            s.setEmail("mail@gmail.com");
            s.setGender(Boolean.TRUE);
            s.setId(1);
            s.setName("dinh");
            s.setPassword("test");
            s.setPhone("094343434");
            s.setRole(new Role(3, "Student", "Student"));
            s.setUserName("dinh");
            listStudent.add(new Student());
        }
        for (int i = 1; i <= 10; i++) {
            list.add(new Batch(i, "batch" + i, new Course(1, "course1", new Date(), new Date()), new Semester(1, "semester 1", 6), new Date(), listStudent));
        }
        return list;
    }
}
