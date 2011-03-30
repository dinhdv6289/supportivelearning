/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import el.dao.BatchDAO;
import el.model.Batch;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author DINH
 */
@ManagedBean
@SessionScoped
public class BatchManagerBean implements Serializable {

    private Batch batch = new Batch();
    private ArrayList<Batch> listBatchs;
    private BatchDAO batchDAO = new BatchDAO();
    private Batch selectedBatch;
    private static boolean panelGripNewBatch;
    private static boolean panelGripBatchs;
    private static boolean panelGripStudentInBatch;
    private static final String REDIRECT = "?faces-redirect=true";
    private static final String THISPAGE = "batchManager.jsf";

    

    /** Creates a new instance of BatchManagerBean */
    public BatchManagerBean() {
        panelGripNewBatch = false;
        panelGripBatchs = true;
        panelGripStudentInBatch = false;
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

    public boolean isPanelGripBatchs() {
        return panelGripBatchs;
    }

    public void setPanelGripBatchs(boolean panelGripBatchs) {
        BatchManagerBean.panelGripBatchs = panelGripBatchs;
    }

    public boolean isPanelGripNewBatch() {
        return panelGripNewBatch;
    }

    public void setPanelGripNewBatch(boolean panelGripNewBatch) {
        BatchManagerBean.panelGripNewBatch = panelGripNewBatch;
    }

    public boolean isPanelGripStudentInBatch() {
        return panelGripStudentInBatch;
    }

    public void setPanelGripStudentInBatch(boolean panelGripStudentInBatch) {
        BatchManagerBean.panelGripStudentInBatch = panelGripStudentInBatch;
    }

    public String onRequestPanelGripNewBatch(boolean newBatch) {
        this.setPanelGripNewBatch(newBatch);
        this.setPanelGripBatchs(false);
        this.setPanelGripStudentInBatch(false);
        return THISPAGE + REDIRECT;
    }

    public String onRequestPanelGripStudentsInBatch(boolean flag) {
        this.setPanelGripStudentInBatch(flag);
        this.setPanelGripNewBatch(false);
        this.setPanelGripBatchs(false);
        return THISPAGE + REDIRECT;
    }

    public String onRequestPanelGripBatchs(boolean flag) {
        this.setPanelGripBatchs(flag);
        this.setPanelGripNewBatch(false);
        this.setPanelGripStudentInBatch(false);
        return THISPAGE + REDIRECT;
    }

    public ArrayList<Batch> getListBatchs() {
        try {
            return listBatchs = batchDAO.list();
        } catch (Exception ex) {
            Logger.getLogger(BatchManagerBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void setListBatchs(ArrayList<Batch> listBatchs) {
        this.listBatchs = listBatchs;
    }

    public String onRowSelectNavigate(SelectEvent event) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedStudent", event.getObject());
        return "changeLearning?faces-redirect=true";
    }
}
