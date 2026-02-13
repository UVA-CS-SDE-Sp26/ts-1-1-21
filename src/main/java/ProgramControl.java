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
            //if no args provided, return the list of files which can be selected
            return listFiles();
        }
        //pass the args to UserInterface to determine the file id and cipher and package as 'request'
        UiRequest request = UserInterface.parseArgs(args);
        return handler.readFileData(request);
    }

    //prints a list of available files
    public String listFiles() {
        return handler.readFiles();
    }
}
