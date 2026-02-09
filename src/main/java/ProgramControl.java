public class ProgramControl implements ProgramCtrl{
    public ProgramControl() {
    }

    @Override
    public String requestFile(int index) {
        return index < 0 ? requestFileWithoutIndex() : requestFileWithIndex(index);
    }

    public String requestFileWithIndex(int index) {
        return "";
    }

    public String requestFileWithoutIndex() {
        return "";
    }

    public void listFiles() {

    }
}
