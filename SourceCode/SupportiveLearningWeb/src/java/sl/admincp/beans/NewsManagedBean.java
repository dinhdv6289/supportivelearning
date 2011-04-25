
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.admincp.beans;

import el.dao.NewsDAO;
import el.model.News;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import sl.utils.beans.MessagesService;
import sl.utils.beans.UtilCheckLoginBean;

/**
 *
 * @author HIEUTT
 */
@ManagedBean
@SessionScoped
public class NewsManagedBean extends UtilCheckLoginBean implements Serializable {

    private ArrayList<News> listNews;
    private News selectedNews;
    private News news = new News();
    private NewsDAO newsDAO = new NewsDAO();
    private static final int BUFFER_SIZE = 1024;
    private static String pathImage = "";
    private static final String REDIRECT = "?faces-redirect=true";
    private static final String THISPAGE = "newsManager.jsf";
    private static boolean panelGroupCreateNews = false;
    private static boolean panelGroupListNews = true;
    private static boolean panelGroupUpdateNews = false;

    /** Creates a new instance of NewsManagedBean */
    public NewsManagedBean() {
        super();
        panelGroupListNews = true;
        panelGroupCreateNews = false;
        panelGroupUpdateNews = false;
    }

    /**
     * @return the listNews
     */
    public ArrayList<News> getListNews() {
        try {
            return listNews = newsDAO.list();
        } catch (Exception ex) {
            Logger.getLogger(NewsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * @param listNews the listNews to set
     */
    public void setListNews(ArrayList<News> listNews) {
        this.listNews = listNews;
    }

    /**
     * @return the news
     */
    public News getNews() {
        return news;
    }

    public static String getPathImage() {
        return pathImage;
    }

    public static void setPathImage(String pathImage) {
        NewsManagedBean.pathImage = pathImage;
    }

    /**
     * @param news the news to set
     */
    public void setNews(News news) {
        this.news = news;
    }

    public String insertNews() {
        try {
            news.setPicture(pathImage);
            int insert = newsDAO.insert(news);
            if (insert > 0) {
                this.setPanelGroupCreateNews(false);
                this.setPanelGroupListNews(true);
                return THISPAGE + REDIRECT;
            } else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(NewsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String updateNews() {
        try {
            selectedNews.setPicture(pathImage);
            boolean update = newsDAO.update(getSelectedNews());
            if (update) {
                this.setPanelGroupUpdateNews(false);
                this.setPanelGroupListNews(true);
                return THISPAGE + REDIRECT;
            } else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(NewsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void deleteNews() {
        try {
            newsDAO.delete(selectedNews);
        } catch (Exception ex) {
            Logger.getLogger(NewsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the selectedNews
     */
    public News getSelectedNews() {
        return selectedNews;
    }

    /**
     * @param selectedNews the selectedNews to set
     */
    public void setSelectedNews(News selectedNews) {
        this.selectedNews = selectedNews;
    }

//    public void handleFileUpload(FileUploadEvent event) {
//        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
//        File result = new File(extContext.getRealPath("/images/news") + "//" + event.getFile().getFileName());
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(result);
//            byte[] buffer = new byte[BUFFER_SIZE];
//            int bulk;
//            InputStream inputStream = event.getFile().getInputstream();
//            while (true) {
//                bulk = inputStream.read(buffer);
//                if (bulk < 0) {
//                    break;
//                }
//                fileOutputStream.write(buffer, 0, bulk);
//                fileOutputStream.flush();
//            }
//            fileOutputStream.close();
//            inputStream.close();
//            FacesMessage sescess = new FacesMessage("suscess", event.getFile().getFileName() + "is Upload");
//            FacesContext.getCurrentInstance().addMessage(null, sescess);
//            pathImage = "images/news/" + event.getFile().getFileName();
//        } catch (IOException e) {
//            e.printStackTrace();
//            FacesMessage error = new FacesMessage("The files were not uploaded!");
//            FacesContext.getCurrentInstance().addMessage(null, error);
//        }
//    }
    public void handleFileUpload(FileUploadEvent event) {
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        File result = new File(extContext.getRealPath("/images/news") + "//" + event.getFile().getFileName());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(result);
            byte[] buffer = new byte[BUFFER_SIZE];
            int bulk;
            InputStream inputStream = event.getFile().getInputstream();
            while (true) {
                bulk = inputStream.read(buffer);
                if (bulk < 0) {
                    break;
                }
                fileOutputStream.write(buffer, 0, bulk);
                fileOutputStream.flush();
            }
            fileOutputStream.close();
            inputStream.close();
            pathImage = "images/news/" + event.getFile().getFileName();
//            if (uploadFile > 0) {
//                MessagesService.showMessage("Succesful " + event.getFile().getFileName() + "is uploaded.");
//            } else {
//                MessagesService.showMessage("Upload " + event.getFile().getFileName() + "is failure.");
//            }
        } catch (IOException e) {
            MessagesService.showMessage("The files were not uploaded!");
            e.printStackTrace();

        }
    }

//    public String onRowSelectNavigate(SelectEvent event) {
//        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedNews", event.getObject());
//        return "DetailNewUpdate?faces-redirect=true";
//    }
//    public String details(SelectEvent event) {
//        return "detailNewsToUpdate" + REDIRECT;
//    }
    /**
     * @return the panelGroupCreateNews
     */
    public boolean isPanelGroupCreateNews() {
        return panelGroupCreateNews;
    }

    /**
     * @param aPanelGroupCreateNews the panelGroupCreateNews to set
     */
    public void setPanelGroupCreateNews(boolean aPanelGroupCreateNews) {
        panelGroupCreateNews = aPanelGroupCreateNews;
    }

    /**
     * @return the panelGroupListNews
     */
    public boolean isPanelGroupListNews() {
        return panelGroupListNews;
    }

    /**
     * @param aPanelGroupListNews the panelGroupListNews to set
     */
    public void setPanelGroupListNews(boolean aPanelGroupListNews) {
        panelGroupListNews = aPanelGroupListNews;
    }

    /**
     * @return the panelGroupUpdateNews
     */
    public boolean isPanelGroupUpdateNews() {
        return panelGroupUpdateNews;
    }

    /**
     * @param aPanelGroupUpdateNews the panelGroupUpdateNews to set
     */
    public void setPanelGroupUpdateNews(boolean aPanelGroupUpdateNews) {
        panelGroupUpdateNews = aPanelGroupUpdateNews;
    }

    public String onRequestCreateNews(boolean flag) {
        this.setPanelGroupCreateNews(flag);
        this.setPanelGroupListNews(false);
        this.setPanelGroupUpdateNews(false);
        return THISPAGE + REDIRECT;
    }

    public String onRequestUpdateNews(boolean flag) {
        this.setPanelGroupUpdateNews(flag);
        this.setPanelGroupListNews(false);
        this.setPanelGroupCreateNews(false);
        return THISPAGE + REDIRECT;
    }

    public String onRequestListNews(boolean flag) {
        this.setPanelGroupListNews(flag);
        this.setPanelGroupUpdateNews(false);
        this.setPanelGroupCreateNews(false);
        return THISPAGE + REDIRECT;
    }
}
