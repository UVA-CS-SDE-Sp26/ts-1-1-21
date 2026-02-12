/**
 * Program Control
 * @author Rory Mcguire
 * This class is responsible for connecting the user interface to the file handler
 */

public class ProgramControl implements ProgramControlInterface{
    private final fileHandler handler;

    public ProgramControl(fileHandler handler) {
        this.handler = handler;
    }

    //returns the deciphered contents of the user-requested file
    @Override
    public String requestFile(String[] args) {
        if(args.length == 0) {
            //if no args provided, print the list of files which can be selected
            listFiles();
        }
        //pass the args to UserInterface to determine the file id and package as 'request'
        UiRequest request = UserInterface.parseArgs(args);
        //String decipheredContents = handler.readFileData(request);
        return "";//decipheredContents;
    }

    //prints a list of available files
    public void listFiles() {
        System.out.println(handler.readFiles());
    }
}
