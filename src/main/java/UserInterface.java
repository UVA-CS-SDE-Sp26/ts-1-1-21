import java.util.List;

public class UserInterface {

    //checks each argument length and returns a UiRequest object containing a fileID variable and cipherKey varaible
    public static UiRequest parseArgs(String[] args){
        //no arguments passed
        if (args.length == 0){
            //return a UiRequest with no arguments
            return new UiRequest(null,null);
        }
        //just file ID passed
        if (args.length == 1){
            //validates the id and if invalid will throw illegal argument exception and cancel the rest of the function
            validateFileID(args[0]);
            return new UiRequest(args[0],null);
        }
        //both arguments passed
        if (args.length == 2){
            //same validation
            validateFileID(args[0]);
            return new UiRequest(args[0],args[1]);
        }
        //if there are too many arguments also throw an exception
        else{
            throw new IllegalArgumentException("Too many command line arguments.");
        }
    }

    //validates our fileID
    public static void validateFileID(String fileID){
        //quick null/empty sanity check
        if (fileID == null || fileID.isEmpty()){
            throw new IllegalArgumentException("File ID cannot be empty");
        }
        //check the length to validate that it is passed in as a string with length of two
        //this can change if project requirements don't need to specify a format like "01"
        if (fileID.length() != 2){
            throw new IllegalArgumentException("Invalid File ID (Length)");
        }
        //validate that it is a number
        //try and catch if changing to integer doesn't work
        try{
            Integer.parseInt(fileID);
        } catch (NumberFormatException error){
            throw new IllegalArgumentException("File ID must be a number");
        }
    }

    //function to call for team member c when no arguments are passed
    public static void printFileList(List<String> files){
        for (int i=0; i < files.size(); i++){
            String fileID;
            //if the number is less or equal to 8 (because id is i+1) then add a 0 before
            if (i <= 8){
                fileID = "0" + Integer.toString(i+1);
            }
            //otherwise just convert to string
            else{
                fileID = Integer.toString(i+1);
            }
            //print as one connected string that is separated by lines
            System.out.println(fileID + " " + files.get(i));
        }
    }

    //prints out the file contents
    public static void printFileContents(String content){
        System.out.println(content);
    }

    //prints the errors for the user
    public static void printError(String error){
        System.out.println(error);
    }
}
