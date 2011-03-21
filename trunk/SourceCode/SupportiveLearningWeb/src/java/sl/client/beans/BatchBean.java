/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.BatchDAO;
import el.model.Batch;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class BatchBean implements Serializable {

    private Batch batch;
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
            this.listBatchs = batchDAO.listBatchOfSemester(id);
        } catch (Exception ex) {
            Logger.getLogger(BatchBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "semesterDetails";
    }

    private void getBatch(Batch batch) {
        try {
            this.batchDetails = batchDAO.getObject(batch);
        } catch (Exception ex) {
            Logger.getLogger(BatchBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
