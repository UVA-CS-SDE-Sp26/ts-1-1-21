/**
 * @author Dimash Adikhan jyv8md
 * 2/9/2026
 */
//Citing my code:https://www.w3schools.com/java/java_files_read.asp
//https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html
//https://docs.oracle.com/javase/8/docs/api/java/util/Map.Entry.html
import java.util.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;



public class fileHandler implements fileHandlerInterface {
    private Map<Integer, String> data = new LinkedHashMap<>(); //holds key value pairs

    public fileHandler() {

        this.loadFiles();
    }

    public void loadFiles() { //loads files
        data.clear();
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
                    data.put(count, file.getName());
                    count++;
                }
            }
        }
    }

    @Override
    public String readFiles() { //Reads all files in the hash (By insert order)
        StringBuilder str = new StringBuilder(); // Using StringBuilder is better for performance in loops
        for (Map.Entry<Integer, String> element : data.entrySet()) {
            Integer key = element.getKey();
            String val = element.getValue();
            str.append(String.format("%02d %s\n", key, val));
        }
        return str.toString();
    }// Returns every file name in the data folder

    @Override
    public String readFileData(UiRequest user) {
        int fileId;
        String keyPath;
        try {
            fileId = Integer.parseInt(user.getFileID());
        } catch (NumberFormatException e) {
            return "Invalid input: Please enter a numeric file ID.";
        }
        String defaultKey;
        String fileCipherKey;

        if(user.getCipherKey()!=null){
            keyPath = user.getCipherKey();
        }
        else{
            keyPath = "key.txt";
        }

        File keyFile = new File("ciphers", keyPath);

        try(BufferedReader keyReader = new BufferedReader(new FileReader(keyFile))) {
            defaultKey = keyReader.readLine();   //first line
            fileCipherKey = keyReader.readLine(); //second line

            if (defaultKey == null || fileCipherKey == null) {
                return "Key file is corrupted.";
            }

        }catch (IOException e){
            return "Error reading the default key file.";
        }



        //Check if file ID exists (01, 02, 03 etc).
        String fileName = data.get(fileId);
        if (fileName == null) {
            return "Invalid file number.";
        }
        File file = new File("data", fileName); //Gets the file from data method
        StringBuilder content = new StringBuilder(); //contents of ciphered data file

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n"); //Add content of ciphered data file
            }
        }catch (IOException e){
            return "Error reading file";
        }

        //decipher
        Cipher deCiphered = new Cipher(defaultKey, fileCipherKey);
        return deCiphered.decipher(content.toString());
    }
}




