/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import el.dao.BatchDAO;
import el.dao.StaffDAO;
import el.model.Batch;
import el.model.Staff;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author DINH
 */
@ManagedBean
@SessionScoped
public class StaffManagerBean implements Serializable {

    private StaffDAO staffDAO = new StaffDAO();
    private Staff staff = new Staff();
    private Staff selectedstaff = new Staff();
    private ArrayList<Staff> listStaffs = new ArrayList<Staff>();
    private Staff[] selectedStaffs;
    private static boolean panelGroupListStaffs;
    private static boolean panelGroupNewStaff;
    private static final String REDIRECT = "?faces-redirect=true";
    private static final String THISPAGE = "staffManager.jsf";
    private Batch batch = new Batch();
    private ArrayList<Batch> listBatchs = new ArrayList<Batch>();

    public ArrayList<Batch> getListBatchs() {
        ArrayList<Batch> result = new ArrayList<Batch>();
        try {
            BatchDAO batchDAO = new BatchDAO();
            result = batchDAO.listBatchNoStaff(selectedstaff);
        } catch (Exception ex) {
            Logger.getLogger(StaffManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean isPanelGroupNewStaff() {
        return panelGroupNewStaff;
    }

    public void setPanelGroupNewStaff(boolean panelGroupNewStaff) {
        StaffManagerBean.panelGroupNewStaff = panelGroupNewStaff;
    }

    public void setListBatchs(ArrayList<Batch> listBatchs) {
        this.listBatchs = listBatchs;
    }

    public Batch getBatch() {
        return batch;
    }

    public Staff getSelectedstaff() {
        return selectedstaff;
    }

    public void setSelectedstaff(Staff selectedstaff) {
        this.selectedstaff = selectedstaff;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public String listBatch(int staffId) {
        String result = "";
        try {
            ArrayList<Batch> batchs = staffDAO.getListBatchsByStaffId(staffId);
            for (Batch b : batchs) {
                result += b.getName() + ", ";
            }
        } catch (Exception ex) {
            Logger.getLogger(StaffManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (result.length() > 2) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    public List<Batch> listBatchsOfstaff(int staffId) {
        ArrayList<Batch> batchs = new ArrayList<Batch>();
        StaffDAO dao = new StaffDAO();
        try {

            batchs = dao.getListBatchsByStaffId(staffId);
        } catch (Exception ex) {
            Logger.getLogger(StaffManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return batchs;
    }

    /** Creates a new instance of StaffManagerBean */
    public StaffManagerBean() {
        panelGroupListStaffs = true;
    }

    @PostConstruct
    public void init() {
        loadListStaffs();
    }

    public ArrayList<Staff> getListStaffs() {
        return listStaffs;
    }

    public void setListStaffs(ArrayList<Staff> listStaffs) {
        this.listStaffs = listStaffs;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public boolean isPanelGroupListStaffs() {
        return panelGroupListStaffs;
    }

    public void setPanelGroupListStaffs(boolean panelGroupListStaffs) {
        StaffManagerBean.panelGroupListStaffs = panelGroupListStaffs;
    }

    public String onRequestGroupListStaffs(boolean flag) {
        this.setPanelGroupListStaffs(flag);
        this.setPanelGroupNewStaff(false);
        return THISPAGE + REDIRECT;
    }

    public String onRequestPanelGroupNewStaff(boolean flag) {
        this.setPanelGroupListStaffs(false);
        this.setPanelGroupNewStaff(flag);
        return THISPAGE + REDIRECT;
    }

    public Staff[] getSelectedStaffs() {
        return selectedStaffs;
    }

    public void setSelectedStaffs(Staff[] selectedStaffs) {
        this.selectedStaffs = selectedStaffs;
    }

    private void loadListStaffs() {
        try {
            if (listStaffs.isEmpty()) {
                listStaffs = staffDAO.list();
            }
        } catch (Exception ex) {
            Logger.getLogger(StaffManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String deleteBatchByStaff() {
        try {

            boolean deleteBatchByStaff = staffDAO.deleteBatchByStaff(selectedstaff, batch);
        } catch (Exception ex) {
            Logger.getLogger(StaffManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String insertStaffAndBatch() {
        try {
            staffDAO.insertStaffAndBatch(selectedstaff, batch);
        } catch (Exception ex) {
            Logger.getLogger(StaffManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String details(SelectEvent event) {

        return "staffdetails" + REDIRECT;
    }

    private String strError;

    public String getStrError() {
        return strError;
    }

    public String insertStaff() {
        try {
            int a = staffDAO.insertStaff(staff);
            if (a == 1) {
                //ok
                this.setPanelGroupListStaffs(true);
                this.setPanelGroupNewStaff(false);
                listStaffs = staffDAO.list();
                strError = "";
                return THISPAGE + REDIRECT;
            }
            if (a == 2) {
                strError = "Email is used";
            }
            if (a == 3) {
                strError = "Username is used";
            }
        } catch (Exception ex) {
            Logger.getLogger(StaffManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
