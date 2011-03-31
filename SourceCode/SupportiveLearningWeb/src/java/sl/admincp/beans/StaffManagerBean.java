/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import el.dao.StaffDAO;
import el.model.Staff;
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
public class StaffManagerBean implements Serializable {

    private StaffDAO staffDAO = new StaffDAO();
    private Staff staff = new Staff();
    private ArrayList<Staff> listStaffs = new ArrayList<Staff>();
    private Staff[] selectedStaffs;
    private static boolean panelGroupListStaffs;
    private static final String REDIRECT = "?faces-redirect=true";
    private static final String THISPAGE = "staffManager.jsf";

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
}
