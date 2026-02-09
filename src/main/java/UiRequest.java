public class UiRequest {

    //kept as a string to keep leading zeroes
    //fileID variable for file selection
    private final String fileID;
    //also kept as string, can be null (default)
    //pass as string, validation comes during group member C or D
    private final String cipherKey;

    public UiRequest(String fileID, String cipherKey) {
        this.fileID = fileID;
        this.cipherKey = cipherKey;
    }

    //checkers that return whether fileID exist
    public boolean hasFileID() {
        return fileID != null;
    }

    public boolean hasCipherKey() {
        return cipherKey != null;
    }

    //getters
    public String getFileID() {
        return fileID;
    }

    public String getCipherKey() {
        return cipherKey;
    }
}

