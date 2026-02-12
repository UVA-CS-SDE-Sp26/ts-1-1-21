/**
 * @author Dimash Adikhan jyv8md
 * 2/9/2026
 */

import java.util.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

//Citing my code:https://www.w3schools.com/java/java_files_read.asp
//https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html
//https://docs.oracle.com/javase/8/docs/api/java/util/Map.Entry.html

public class fileHandler implements fileHandlerInterface{
    private Map<String, String> data = new LinkedHashMap<>();

    public fileHandler(){
        data = new HashMap<>();
        this.loadFiles();
    }


    private void loadFiles(){ //loads files
        File folder = new File("data");

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Error: data folder is not found in TopSecret program" + System.getProperty("user.dir"));
            return;
        }
        File[] files = folder.listFiles();
        int count = 0;

        if (files != null) {
            Arrays.sort(files);
            for (File file : files) {
                if (file.isFile()&&!file.getName().startsWith(".")) {
                    String formatted = String.format("%02d", count);
                    data.put(formatted, file.getName());
                    count++;
                }
            }
        }
    }
    @Override
    public String readFiles(){ //Reads all files in the hash (By insert order)

        String str = "";
        for(Map.Entry<String, String> element: data.entrySet()){
            String key = element.getKey();
            String val = element.getValue();
            str += key + " " + val + "\n";
        }
        return str;
    }// Returns every file name in the data folder

    @Override
    public String readFileData(String userIn){ //Reads in data by ID (STARTS FROM 00)
        String fileName = data.get(userIn);
        if (fileName==null) {
            return "Invalid file number.";
        }

        File file = new File("data", fileName);
        String content = "";

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line=br.readLine()) != null){
                content+=(line)+"\n";
            }
        }
        catch (IOException e){
            return "Error reading the file.";
        }
        return content;
    }

}
