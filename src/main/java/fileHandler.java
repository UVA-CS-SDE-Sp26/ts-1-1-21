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

public class fileHandler implements fileHandlerInterface {
    private Map<String, String> data = new LinkedHashMap<>();

    public fileHandler() {

        this.loadFiles();
    }

    public void loadFiles() { //loads files
        File folder = new File("data");

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Error: data folder is not found in TopSecret program" + System.getProperty("user.dir"));
            return;
        }
        File[] files = folder.listFiles();
        int count = 1;

        if (files != null) {
            Arrays.sort(files);
            for (File file : files) {
                if (file.isFile() && !file.getName().startsWith(".")) {
                    String formatted = String.format("%02d", count);
                    data.put(formatted, file.getName());
                    count++;
                }
            }
        }
    }

    @Override
    public String readFiles() { //Reads all files in the hash (By insert order)

        String str = "";
        for (Map.Entry<String, String> element : data.entrySet()) {
            String key = element.getKey();
            String val = element.getValue();
            str += key + " " + val + "\n";
        }
        return str;
    }// Returns every file name in the data folder

    @Override
    public String readFileData(UiRequest user) {
        String userIn = user.getFileID();
        String defaultKey;
        String fileCipherKey;

        File keyFile = new File("ciphers", "key.txt");

        try(BufferedReader keyReader = new BufferedReader(new FileReader(keyFile))) {
            defaultKey = keyReader.readLine();   //first line
            fileCipherKey = keyReader.readLine(); //second line

            if (defaultKey == null || fileCipherKey == null) {
                return "Key file is corrupted.";
            }

        }catch (IOException e){
            return "Error reading the default key file.";
        }

        String finalCipherKey;
        String userCipherKey = user.getCipherKey();

        if(userCipherKey != null){ //This if statement determines which key we're using
            finalCipherKey = userCipherKey;
        }else{
            finalCipherKey = fileCipherKey;
        }
        //Check if file ID exists
        String fileName = data.get(userIn);
        if(fileName == null) {
            return "Invalid file number.";
        }

        File file = new File("data", fileName); //Gets the file from data method
        StringBuilder content = new StringBuilder(); //contents of ciphered data file

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n"); //Add
            }
        }catch (IOException e){
            return "Error reading file";
        }

        //decrypt
        Cipher deCiphered = new Cipher(defaultKey, finalCipherKey);
        return deCiphered.decipher(content.toString());
    }
}




