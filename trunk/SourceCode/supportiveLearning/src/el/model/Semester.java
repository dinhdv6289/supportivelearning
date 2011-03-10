/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package el.model;

/**
 *
 * @author TuyenPV
 */
public class Semester {
    private int id;
    private String semesterName;
    private int semesterTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public int getSemesterTime() {
        return semesterTime;
    }

    public void setSemesterTime(int semesterTime) {
        this.semesterTime = semesterTime;
    }

    public Semester(int id, String semesterName, int semesterTime) {
        this.id = id;
        this.semesterName = semesterName;
        this.semesterTime = semesterTime;
    }
    
}
