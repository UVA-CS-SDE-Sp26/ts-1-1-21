/**
 * Commmand Line Utility
 */
public class TopSecret {

    public static void main(String[] args) {
        if(args.length > 1) {
            System.out.println("Too many arguments");
            java.lang.System.exit(1);
        }
        ProgramControl control = new ProgramControl();
        String fileContents = control.requestFile(args.length > 0 ? Integer.parseInt(args[0]) : -1);
    }

}
