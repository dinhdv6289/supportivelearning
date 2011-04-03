/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import el.dao.BatchDAO;
import el.model.Batch;
import el.model.Course;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author DINH
 */
@ManagedBean
@SessionScoped
public class BatchManagerBean implements Serializable {

    private Batch batch = new Batch();
    private ArrayList<Batch> listBatchs = new ArrayList<Batch>();
    private BatchDAO batchDAO = new BatchDAO();
    private Batch selectedBatch;
    private Course selectedCourse;
    private static boolean panelGroupNewBatch;
    private static boolean panelGroupBatchs;
    private static boolean panelGroupStudentInBatch;
    private static final String REDIRECT = "?faces-redirect=true";
    private static final String THISPAGE = "batchManager.jsf";

    /** Creates a new instance of BatchManagerBean */
    public BatchManagerBean() {
    }

    @PostConstruct
    public void init() {
        getListBatchs();
        panelGroupNewBatch = false;
        panelGroupBatchs = true;
        panelGroupStudentInBatch = false;
    }

    public Course getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    
    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Batch getSelectedBatch() {
        return selectedBatch;
    }

    public void setSelectedBatch(Batch selectedBatch) {
        this.selectedBatch = selectedBatch;
    }

    public boolean isPanelGroupBatchs() {
        return panelGroupBatchs;
    }

    public void setPanelGroupBatchs(boolean panelGripBatchs) {
        BatchManagerBean.panelGroupBatchs = panelGripBatchs;
    }

    public boolean isPanelGroupNewBatch() {
        return panelGroupNewBatch;
    }

    public void setPanelGroupNewBatch(boolean panelGripNewBatch) {
        BatchManagerBean.panelGroupNewBatch = panelGripNewBatch;
    }

    public boolean isPanelGroupStudentInBatch() {
        return panelGroupStudentInBatch;
    }

    public void setPanelGroupStudentInBatch(boolean panelGripStudentInBatch) {
        BatchManagerBean.panelGroupStudentInBatch = panelGripStudentInBatch;
    }

    public String onRequestPanelGroupNewBatch(boolean newBatch) {
        this.setPanelGroupNewBatch(newBatch);
        this.setPanelGroupBatchs(false);
        this.setPanelGroupStudentInBatch(false);
        return THISPAGE + REDIRECT;
    }

    public String onRequestPanelGroupStudentsInBatch(boolean flag) {
        this.setPanelGroupStudentInBatch(flag);
        this.setPanelGroupNewBatch(false);
        this.setPanelGroupBatchs(false);
        return THISPAGE + REDIRECT;
    }

    public String onRequestPanelGroupBatchs(boolean flag) {
        this.setPanelGroupBatchs(flag);
        this.setPanelGroupNewBatch(false);
        this.setPanelGroupStudentInBatch(false);
        return THISPAGE + REDIRECT;
    }

    public ArrayList<Batch> getListBatchs() {
        try {
            if (listBatchs.isEmpty()) {
                listBatchs = batchDAO.list();
            }
            return listBatchs;
        } catch (Exception ex) {
            Logger.getLogger(BatchManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void setListBatchs(ArrayList<Batch> listBatchs) {
        this.listBatchs = listBatchs;
    }


    public ArrayList<Batch> loadBatchs() {
        try {
                listBatchs = batchDAO.list();
            return listBatchs;
        } catch (Exception ex) {
            Logger.getLogger(BatchManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public String insertBatch(){
        try {
            batchDAO.insert(batch);
            this.setPanelGroupBatchs(true);
            this.setPanelGroupNewBatch(false);
            this.setPanelGroupStudentInBatch(false);
            loadBatchs();
            return THISPAGE + REDIRECT;
        } catch (Exception ex) {
            Logger.getLogger(BatchManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
