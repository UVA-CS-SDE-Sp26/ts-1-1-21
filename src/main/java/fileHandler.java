/**
 * @author Dimash Adikhan
 * 2/9/2026
 */

import java.util.*;
import java.io.File;



public class fileHandler {
    private Map<String, String> data = new HashMap<>();

    public String readFiles() {
        File folder = new File("data"); //data file directory
        if (!folder.exists() || !folder.isDirectory()) {
            return "Data folder is not found in TopSecret";
        }

        StringBuilder str = new StringBuilder("Reading: ");
        File[] files = folder.listFiles();
        int count = 0;

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String formatted = String.format("%02d", count); //
                    data.put(formatted, file.getName()); //adds files into data map.
                    str.append(file.getName()).append(" ");
                    count++;
                }
            }
        }
        return str.toString();
    }// Returns every file in the data folder

    public String getFileName(){
        return "";
    }// Returns the file name of certain files in data directory

    public String getUserInput(){

    }

    public String readFileData(int UserIn){ //Reads in the data
        return "";
    }

}
