/**
 * Commmand Line Utility
 */
public class TopSecret {

    public static void main(String[] args) {
        ProgramControl control = new ProgramControl(new fileHandler());
        String fileContents = control.requestFile(args);
        System.out.println(fileContents);
    }

}
