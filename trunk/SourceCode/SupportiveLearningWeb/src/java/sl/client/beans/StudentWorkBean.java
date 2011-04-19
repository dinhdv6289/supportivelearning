/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.client.beans;

import el.dao.StudentWorkDAO;
import el.model.Student;
import el.model.StudentWork;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import el.model.Assignment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.event.FileUploadEvent;
import sl.utils.beans.EachSession;
import sl.utils.beans.LoginService;
import sl.utils.beans.MessagesService;
import sl.utils.beans.UtilCheckLoginBean;

/**
 *
 * @author DINHDV
 */
@ManagedBean
@SessionScoped
public class StudentWorkBean  implements Serializable {

    private Student student = new Student();
    private StudentWork studentWork = new StudentWork();
    private ArrayList<StudentWork> listStudentWorksOfStudent = new ArrayList<StudentWork>();
    private ArrayList<StudentWork> listStudentWorksOfEachStudent = new ArrayList<StudentWork>();
    private ArrayList<StudentWork> listMarks;
    private StudentWorkDAO studentWorkDAO = new StudentWorkDAO();
    private Assignment assignment = new Assignment();
    private String file;
    private static boolean panelGroupFormMarkView = true;
    private static boolean panelGroupFormDisplayResult = false;
    private static final String REDIRECT = "?faces-redirect=true";
    private static final int BUFFER_SIZE = 1024;

    /** Creates a new instance of StudentWorkBean */
    public StudentWorkBean() {
        loadStudentWorks();
    }

    public boolean isPanelGroupFormDisplayResult() {
        return panelGroupFormDisplayResult;
    }

    public void setPanelGroupFormDisplayResult(boolean panelGroupFormDisplayResult) {
        StudentWorkBean.panelGroupFormDisplayResult = panelGroupFormDisplayResult;
    }

    public boolean isPanelGroupFormMarkView() {
        return panelGroupFormMarkView;
    }

    public void setPanelGroupFormMarkView(boolean panelGroupFormMarkView) {
        StudentWorkBean.panelGroupFormMarkView = panelGroupFormMarkView;
    }

    public ArrayList<StudentWork> getListStudentWorksOfStudent() {
        return listStudentWorksOfStudent;
    }

    public void setListStudentWorksOfStudent(ArrayList<StudentWork> listStudentWorksOfStudent) {
        this.listStudentWorksOfStudent = listStudentWorksOfStudent;
    }

    public ArrayList<StudentWork> getListStudentWorksOfEachStudent() {
        return listStudentWorksOfEachStudent;
    }

    public void setListStudentWorksOfEachStudent(ArrayList<StudentWork> listStudentWorksOfEachStudent) {
        this.listStudentWorksOfEachStudent = listStudentWorksOfEachStudent;
    }

    public StudentWork getStudentWork() {
        return studentWork;
    }

    public void setStudentWork(StudentWork studentWork) {
        this.studentWork = studentWork;
    }

    public ArrayList<StudentWork> getListMarks() {
        return listMarks;
    }

    public void setListMarks(ArrayList<StudentWork> listMarks) {
        this.listMarks = listMarks;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    private void loadStudentWorks() {
        try {
            listStudentWorksOfStudent = studentWorkDAO.listMarkUpdate();
        } catch (Exception ex) {
            Logger.getLogger(StudentWorkBean.class.getName()).log(Level.SEVERE, null, ex);
            listStudentWorksOfStudent = null;
        }
    }

    public void loadStudentWorksOfEachStudent() {
        try {
            Student std = (Student) EachSession.getObjectFromSession("accountId");
            if (std != null) {
                listStudentWorksOfEachStudent = studentWorkDAO.listStudentWorkByStudentId(std.getId());
            } else {
                LoginService.loginService(null);
            }
        } catch (Exception ex) {
            Logger.getLogger(StudentWorkBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String myFiles() {
        loadStudentWorksOfEachStudent();
        return "myFiles" + REDIRECT;
    }

    public String loadMarks() {
        try {
            if (!student.getRollNumber().equals("") || student.getRollNumber() != null) {
                ArrayList<StudentWork> listStudentWorkByRollNumber = studentWorkDAO.listStudentWorkByRollNumber(student.getRollNumber());
                if (listStudentWorkByRollNumber.size() > 0) {
                    this.listMarks = studentWorkDAO.listStudentWorkByRollNumber(student.getRollNumber());
                    this.setPanelGroupFormMarkView(false);
                    this.setPanelGroupFormDisplayResult(true);
                } else {
                    MessagesService.showMessage("Not have Student Rollnumber: " + student.getRollNumber());
                }
            } else {
                MessagesService.showMessage("Roll number is not null!");
            }
        } catch (Exception ex) {
            Logger.getLogger(StudentWorkBean.class.getName()).log(Level.SEVERE, null, ex);
            MessagesService.showMessage(ex.getMessage());
        }
        return null;
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);
            cell.setCellStyle(cellStyle);
        }
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "prime_logo.png";

        pdf.add(Image.getInstance(logo));
    }

    /** Creates a new instance of FileUploadController */
    public void handleFileUpload(FileUploadEvent event) {
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        File result = new File(extContext.getRealPath("/documents/assignmentFiles") + "//" + event.getFile().getFileName());
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
            int uploadFile = uploadFile("documents/assignmentFiles/" + event.getFile().getFileName());
            if (uploadFile > 0) {
                MessagesService.showMessage("Succesful " + event.getFile().getFileName() + "is uploaded.");
            } else {
                MessagesService.showMessage("Upload " + event.getFile().getFileName() + "is failure.");
            }
        } catch (IOException e) {
            MessagesService.showMessage("The files were not uploaded!");
            e.printStackTrace();

        }
    }

    private int uploadFile(String file) {
        int result = 0;
        try {
            Student std = (Student) EachSession.getObjectFromSession("accountId");
            if (std != null) {
                studentWork.setStudent(std);
                studentWork.setFileUpload(file);
                studentWork.setAssignment(assignment);
                studentWork.setDateUpload(new Date());
                StudentWork myAssignment = studentWorkDAO.getMyAssignment(studentWork);
                if (myAssignment == null) {
                    result = studentWorkDAO.insert(studentWork);
                } else {
                    result = studentWorkDAO.updateAssignmentWork(studentWork);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(StudentWorkBean.class.getName()).log(Level.SEVERE, null, ex);
            result = 0;
        }
        return result;
    }
}
