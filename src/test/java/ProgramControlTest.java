/**
 * ProgramControlTest
 * @author Rory McGuire
 * Contains JUnit tests for the class ProgramControl
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class ProgramControlTest {
    //private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    //private final PrintStream originalOut = System.out;
    private AutoCloseable closeable;

    @Mock
    private fileHandler mockHandler;

    @Mock
    private ProgramControl mockControl;

    @BeforeEach
    public void setUp() {
        //set up mocks
        closeable = MockitoAnnotations.openMocks(this);
        //set up catch-stream for print output
        //System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() throws Exception {
        //teardown mocks
        closeable.close();
        //teardown catch-stream
        //System.setOut(originalOut);
    }

    ////Testing listFiles():////
    /* Cases:
    * call to fileHandler returns a String
    * call to fileHandler returns null
     */

    //fileHandler.listFiles returns a String
    @Test
    void testListFilesStringList() {
        String fileList = "01 file1.txt\n" +
                "02 file2.txt\n" +
                "03 file3.txt";

        ProgramControl control = new ProgramControl(mockHandler);
        when(mockHandler.readFiles()).thenReturn(fileList);
        assertEquals(fileList, control.listFiles(), "listFiles() failed to return the correct String when call\n"
                                                            +"to fileHandler.readFiles() returned a String");
        //assertEquals(fileList + System.lineSeparator(), outputStream.toString(),
        //        "listFiles() printed incorrect value to out");
    }

    //fileHandler.listFiles returns null
    @Test
    void testListFilesNullList() {
        String fileList = null;

        ProgramControl control = new ProgramControl(mockHandler);
        when(mockHandler.readFiles()).thenReturn(fileList);
        assertEquals(fileList, control.listFiles(), "listFiles() failed to return null when call\n"
                +"to fileHandler.readFiles() returned null");
        //assertEquals(fileList + System.lineSeparator(), outputStream.toString(),
        //        "listFiles() printed incorrect value to out");
    }

    ////Testing requestFile()////
    /* Cases:
    * empty args - returns listFiles()
    * 1 args - return ciphered contents
    * 2 args - return deciphered contents
    * >2 args -
     */

    //requestFile called with no args
    @Test
    void testRequestFileNoArgs() {
        String[] args = {};
        String fileList = "01 file1.txt\n" +
                "02 file2.txt\n" +
                "03 file3.txt";

        mockControl = new ProgramControl(mockHandler);
        when(mockControl.listFiles()).thenReturn(fileList);


        assertEquals(fileList, mockControl.requestFile(args),
                "requestFile() failed to return listArgs() when no args were provided");
        //assertEquals(fileList + System.lineSeparator(), outputStream.toString(),
        //        "requestFile() did not print file list as intended");
    }

    //valid arg is passed
    @Test
    void testRequestFileWithInput() {
        String fileContents = "File Contents!";
        String[] args = {"1"};
        String fileList = "01 file1.txt\n" +
                "02 file2.txt\n" +
                "03 file3.txt";

        ProgramControl control = new ProgramControl(mockHandler);
        //when(handler.readFileData()).thenReturn(fileContents);

        //when(handler.readFiles()).thenReturn(fileList);
        //control.listFiles();
        //assertEquals(fileList + System.lineSeparator(), outputStream.toString(),
        //        "listFiles() printed incorrect value to out");
        assertEquals(fileContents, control.requestFile(args), "");
    }
}