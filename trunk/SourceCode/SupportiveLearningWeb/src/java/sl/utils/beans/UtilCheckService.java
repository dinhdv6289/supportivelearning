/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.utils.beans;

import java.io.File;

/**
 *
 * @author DINH
 */
public class UtilCheckService {

    public boolean checkExistFile(String path, String fileName) {
        boolean flag = false;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if(listOfFiles[i].getName().equalsIgnoreCase(fileName)){
                    flag = true;
                }
            }
        }
        return flag;
    }
}
