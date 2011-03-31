
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sl.admincp.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import el.dao.NewsDAO;
import el.model.News;
import el.model.Student;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.print.attribute.standard.OutputDeviceAssigned;
import org.primefaces.event.FileUploadEvent;
import sl.utils.beans.EachSession;
import sl.utils.beans.MessagesService;

/**
 *
 * @author HIEUTT
 */
@ManagedBean
@RequestScoped
public class NewsManagedBean implements Serializable {

    /**
     * @return the pathImage
     */
    public static String getPathImage() {
        return pathImage;
    }

    /**
     * @param aPathImage the pathImage to set
     */
    public static void setPathImage(String aPathImage) {
        pathImage = aPathImage;
    }

    private ArrayList<News> listNews;
    private News selectedNews = new News();
    private News news;
    private NewsDAO newsDAO = new NewsDAO();
    private static final int BUFFER_SIZE = 1024;
    private static String pathImage = "";

    /** Creates a new instance of NewsManagedBean */
    public NewsManagedBean() {
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

    /**
     * @param news the news to set
     */
    public void setNews(News news) {
        this.news = news;
    }

    public String insertNews() {
        try {
            newsDAO.insert(news);
            return "listNews";
        } catch (Exception ex) {
            Logger.getLogger(NewsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String updateNews() {
        try {
            if (newsDAO.update(getSelectedNews())) {
                return "listNews";
            }
            return null;
        } catch (Exception ex) {
            Logger.getLogger(NewsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void deleteNews() {
        try {
            newsDAO.delete(news);
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
            FacesMessage sescess = new FacesMessage("suscess", event.getFile().getFileName() + "is Upload");
            FacesContext.getCurrentInstance().addMessage(null, sescess);
            setPathImage("images/" + event.getFile().getFileName());
        } catch (IOException e) {
            MessagesService.showMessage("The files were not uploaded!");
            e.printStackTrace();
        }
    }
//    private int uploadFile(String file) {
//        int result = 0;
//        try {
//            News std = (News) EachSession.getObjectFromSession("accountId");
//            if (std != null) {
//                news.setStudent(std);
//                studentWork.setFileUpload(file);
//                studentWork.setAssignment(assignment);
//                result = studentWorkDAO.insert(studentWork);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(StudentWorkBean.class.getName()).log(Level.SEVERE, null, ex);
//            result = 0;
//        }
//        return result;
//    }
}

