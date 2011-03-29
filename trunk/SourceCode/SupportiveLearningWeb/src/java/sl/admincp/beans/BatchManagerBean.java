/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import el.dao.BatchDAO;
import el.dao.StudentDAO;
import el.model.Batch;
import el.model.Student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    /** Creates a new instance of BatchManagerBean */
    public BatchManagerBean() {
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

    public ArrayList<Batch> getListBatchs() {
        try {
            listBatchs = batchDAO.list();
            return listBatchs;
        } catch (Exception ex) {
            Logger.getLogger(BatchManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void setListBatchs(ArrayList<Batch> listBatchs) {
        this.listBatchs = listBatchs;
    }
}
