import org.junit.jupiter.api.Test;
//necessary so I can make sure anything that's printed can be checked
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserInterfaceTest {
    //IMPORTANT FOR ALL STRING PRINT TESTS
    //setup helper function so we can assert the values of our print tests
    private String captureSysOut(Runnable action){
        //captures the original system.out so we can restore after test runs
        PrintStream original = System.out;
        //create our new output method that we can test against
        ByteArrayOutputStream newOut = new ByteArrayOutputStream();
        //redirects our system out to our new created testable method
        System.setOut(new PrintStream(newOut));
        try{
            //run the method
            action.run();
            //convert our new output to a string to test against what we want
            return newOut.toString();
        } finally {
            //restore our original system out for any future tests
            System.setOut(original);
        }
    }

    //helper function to have less assertions for UiRequest objects
    private void assertUiRequest(UiRequest req, String expectedID, String expectedKey){
        assertNotNull(req, "UiRequest should not be null");
        assertEquals(expectedID, req.getFileID(), "fileID doesn't match");
        assertEquals(expectedKey, req.getCipherKey(), "CipherKey doesn't match");
    }

    /* parseArgs tests:
    1. Zero arguments - returns UiRequest(null,null)
    2. One Argument (valid fileID) - returns UiRequest(fileID,null)
    3. Two Arguments (valid fileID, anything for chipher) - returns UiRequest(fileID, cipherKey)
    4. Three arguments - throws IllegalArgumentException (too many args)
    5. One Argument (invalid fileID) - throws illegalArgumentException
    6. Two Arguments (invalid fileID) - throws illegalArgumentException
    7. Two Arguments (valid fileID, empty cipher) - returns UiRequest(fileID, "")
    8. Two Arguments (valid fileID, special characters) - returns same characters in cipherKey
     */

    @Test
    void zeroArgParseArgs() {
        UiRequest req = UserInterface.parseArgs(new String[]{});
        assertUiRequest(req,null,null);
    }
    @Test
    void oneValidArgParseArgs(){
        UiRequest req = UserInterface.parseArgs(new String[]{"01"});
        assertUiRequest(req,"01",null);
    }
    @Test
    void twoValidArgParseArgs(){
        UiRequest req = UserInterface.parseArgs(new String[]{"03","keyy.txt"});
        assertUiRequest(req,"03","keyy.txt");
    }
    @Test
    void invalidThreeArgParseArgs(){
        assertThrows(
                IllegalArgumentException.class,
                () -> UserInterface.parseArgs(new String[] {"10","key","extra"})
        );
    }

    @Test
    void invalidFileIDOneArgParseArgs(){
        assertThrows(
                IllegalArgumentException.class,
                () -> UserInterface.parseArgs(new String[] {"A3"})
        );
    }
    @Test
    void invalidFileIDTwoArgParseArgs(){
        assertThrows(
                IllegalArgumentException.class,
                () -> UserInterface.parseArgs(new String[] {"A3","key"})
        );
    }
    @Test
    void emptyCipherParseArgs(){
        UiRequest req = UserInterface.parseArgs(new String[]{"03",""});
        assertUiRequest(req,"03","");
    }
    @Test
    void wierdCipherParseArgs(){
        UiRequest req = UserInterface.parseArgs(new String[]{"03","-@9/"});
        assertUiRequest(req,"03","-@9/");
    }

    /* validateFileID tests:
    1. number valid chekcs - no exception
    2. null check - throws cannot be empty
    3. empty check - throws cannot be empty
    4. length check(1) - throws invalid fileID length
    5. length check(3) - throws invalid fileID length
    6. correct length, wrong format (whitespace " 1") - throws must be a number
    7. correct length, wrong format (non numbers "x2") - throws must be a number
    8. negative number (-1) - throws must be greater than 0
    9. zero test - throws must be greater than 0
     */
    @Test
    void validValidateFileID(){
        assertDoesNotThrow(() -> UserInterface.validateFileID("01"));
        assertDoesNotThrow(() -> UserInterface.validateFileID("10"));
        assertDoesNotThrow(() -> UserInterface.validateFileID("99"));
    }
    @Test
    void nullArgValidateFileID() {
        assertThrows(
                IllegalArgumentException.class,
                () -> UserInterface.validateFileID(null)
        );
    }
    @Test
    void emptyArgValidateFileID() {
        assertThrows(
                IllegalArgumentException.class,
                () -> UserInterface.validateFileID("")
        );
    }
    @Test
    void lengthShortValidateFileID() {
        assertThrows(
                IllegalArgumentException.class,
                () -> UserInterface.validateFileID("1")
        );
    }
    @Test
    void lengthLongValidateFileID() {
        assertThrows(
                IllegalArgumentException.class,
                () -> UserInterface.validateFileID("003")
        );
    }
    @Test
    void spaceFormatValidateFileID() {
        assertThrows(
                IllegalArgumentException.class,
                () -> UserInterface.validateFileID(" 3")
        );
    }
    @Test
    void nonNumberValidateFileID() {
        assertThrows(
                IllegalArgumentException.class,
                () -> UserInterface.validateFileID("x3")
        );
    }
    @Test
    void negativeValidateFileID() {
        assertThrows(
                IllegalArgumentException.class,
                () -> UserInterface.validateFileID("-1")
        );
    }
    @Test
    void zeroValidateFileID() {
        assertThrows(
                IllegalArgumentException.class,
                () -> UserInterface.validateFileID("00")
        );
    }

    /* printFileList tests:
    1. Empty list - prints nothing
    2. 1 entry - prints 01 name \n
    3. 9 entries - prints 0_ up til 09 with name \n
    4. 10 entries - prints up to 09 then 10 with name \n
    5. wierd file name - prints file name as is
    6. null file - prints null
    7. file list is null - throws null exception
     */

    @Test
    void emptyPrintFileList() {
        String output = captureSysOut(
                () -> UserInterface.printFileList(List.of())
        );
        assertEquals("",output);
    }
    @Test
    void onePrintFileList() {
        String output = captureSysOut(
                () -> UserInterface.printFileList(List.of("fileA.txt"))
        );
        assertEquals("01 fileA.txt" + System.lineSeparator(),output);
    }
    @Test
    void ninePrintFileList() {
        String output = captureSysOut(
                () -> UserInterface.printFileList(List.of("fileA.txt","fileA.txt","fileA.txt","fileA.txt","fileA.txt","fileA.txt","fileA.txt","fileA.txt","fileA.txt"))
        );
        //just make sure it contains 09 fileA.txt
        assertTrue(output.contains("09 fileA.txt" + System.lineSeparator()));
    }
    @Test
    void tenPrintFileList() {
        String output = captureSysOut(
                () -> UserInterface.printFileList(List.of("fileA.txt","fileA.txt","fileA.txt","fileA.txt","fileA.txt","fileA.txt","fileA.txt","fileA.txt","fileA.txt","fileA.txt"))
        );
        assertTrue(output.contains("10 fileA.txt" + System.lineSeparator()));
    }
    @Test
    void wierdFileNamePrintFileList() {
        String output = captureSysOut(
                () -> UserInterface.printFileList(List.of("@@-2-2nnZ!"))
        );
        assertEquals("01 @@-2-2nnZ!" + System.lineSeparator(), output);
    }
    //in case on of our files is null we should still print it
    //need to use array list because we want just one file to be null,
    //null list would throw an exception
    @Test
    void nullFilePrintFileList() {
        List<String> files = new ArrayList<>();
        files.add(null);
        String output = captureSysOut(
                () -> UserInterface.printFileList(files)
        );
        assertEquals("01 null" + System.lineSeparator(), output);
    }
    @Test
    void nullListPrintFileList() {
        assertThrows(NullPointerException.class,
                () -> UserInterface.printFileList(null)
        );
    }

    /* printFileContents tests:
    1. Normal string - prints string then a new line
    2. Empty string - prints new line
    3. Null string - prints null and a new line
     */

    @Test
    void NormalPrintFileContents() {
        String output = captureSysOut(
                () -> UserInterface.printFileContents("hello world")
        );
        assertEquals("hello world" + System.lineSeparator(), output);
    }
    @Test
    void emptyPrintFileContents() {
        String output = captureSysOut(
                () -> UserInterface.printFileContents("")
        );
        assertEquals(System.lineSeparator(), output);
    }
    @Test
    void nullPrintFileContents() {
        String output = captureSysOut(
                () -> UserInterface.printFileContents(null)
        );
        assertEquals("null" + System.lineSeparator(), output);
    }
    /* printError tests:
    1. normal message passed - prints error string passed
    2. empty message passed - prints new line
    3. null message passed - prints null and a new line
     */

    @Test
    void NormalPrintError() {
        String output = captureSysOut(
                () -> UserInterface.printFileContents("error occurred")
        );
        assertEquals("error occurred" + System.lineSeparator(), output);
    }
    @Test
    void emptyPrintError() {
        String output = captureSysOut(
                () -> UserInterface.printFileContents("")
        );
        assertEquals(System.lineSeparator(), output);
    }
    @Test
    void nullPrintError() {
        String output = captureSysOut(
                () -> UserInterface.printFileContents(null)
        );
        assertEquals("null" + System.lineSeparator(), output);
    }
}