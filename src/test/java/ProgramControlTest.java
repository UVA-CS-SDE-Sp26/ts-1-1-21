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
    * 1 args (valid) - return ciphered contents
        * invalid arg (<1, not an int, empty) - IllegalArgument Exception
    * 2 args (valid) - return deciphered contents
        * first arg invalid (<1, not an int, empty) - IllegalArgumentException
        * second arg invalid (empty String) - return ciphered contents
    * >2 args - IllegalArgumentException
     */

    //requestFile called with no args
    @Test
    void testRequestFileNoArgs() {
        String[] args = {};
        String fileList = "01 file1.txt\n" +
                "02 file2.txt\n" +
                "03 file3.txt";

        ProgramControl control = new ProgramControl(mockHandler);
        when(mockHandler.readFiles()).thenReturn(fileList);


        assertEquals(fileList, control.requestFile(args),
                "requestFile() failed to return the file list when no args were provided");
    }

    //1 arg is passed
    @Test
    void testRequestFileArg1Valid() {
        String fileContentsCiphered = "ciphertextxyz" ;
        String fileContentsDeciphered = "File Contents!";
        String[] args = {"1"};
        String fileList = "01 file1.txt\n" +
                "02 file2.txt\n" +
                "03 file3.txt";

        ProgramControl control = new ProgramControl(mockHandler);
        //UiRequest request = new UiRequest(args[1], args[2])
        //Cannot mock UserInterface.parseArgs because it is static (?)
        when(mockHandler.readFileData(any(UiRequest.class))).thenReturn(fileContentsCiphered);

        assertEquals(fileContentsCiphered, control.requestFile(args),
                "when 1 valid arg was passed, requestFile should have returned the ciphered text");
    }

    //2 valid args are passed
    @Test
    void testRequestFileArg1ValidArg2Valid() {
        String fileContentsCiphered = "ciphertextxyz" ;
        String fileContentsDeciphered = "File Contents!";
        String[] args = {"1", "decipher-key"};
        String fileList = "01 file1.txt\n" +
                "02 file2.txt\n" +
                "03 file3.txt";

        ProgramControl control = new ProgramControl(mockHandler);

        //Cannot mock UserInterface.parseArgs because it is static (?)
        when(mockHandler.readFileData(any(UiRequest.class))).thenReturn(fileContentsDeciphered);

        assertEquals(fileContentsDeciphered, control.requestFile(args),
                "when 1 valid arg was passed, requestFile should have returned the ciphered text");
    }

    //1 invalid non-int arg is passed
    @Test
    void testRequestFileArg1NotInt() {
        String fileContentsCiphered = "ciphertextxyz" ;
        String fileContentsDeciphered = "File Contents!";
        String[] args = {"not an integer"};
        String fileList = "01 file1.txt\n" +
                "02 file2.txt\n" +
                "03 file3.txt";

        ProgramControl control = new ProgramControl(mockHandler);
        //UiRequest request = new UiRequest(args[1], args[2])
        //Cannot mock UserInterface.parseArgs because it is static (?)
        when(mockHandler.readFileData(any(UiRequest.class))).thenReturn(fileContentsCiphered);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> control.requestFile(args)
        );
        assertEquals("File ID must be a number", exception.getMessage(),
                "requestFile did not throw correct message when one non-int arg was passed to it");
    }

    //1 invalid arg <1 is passed
    @Test
    void testRequestFileArg1LessThanOne() {
        String fileContentsCiphered = "ciphertextxyz" ;
        String fileContentsDeciphered = "File Contents!";
        String[] args = {"0"};
        String fileList = "01 file1.txt\n" +
                "02 file2.txt\n" +
                "03 file3.txt";

        ProgramControl control = new ProgramControl(mockHandler);
        //UiRequest request = new UiRequest(args[1], args[2])
        //Cannot mock UserInterface.parseArgs because it is static (?)
        when(mockHandler.readFileData(any(UiRequest.class))).thenReturn(fileContentsCiphered);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> control.requestFile(args)
        );
        assertEquals("Invalid File ID (must be greater than 0)", exception.getMessage(),
                "requestFile did not throw correct message when one arg < 1 was passed to it");
    }

    //1 invalid arg <1 is passed
    @Test
    void testRequestFileArg1Empty() {
        String fileContentsCiphered = "ciphertextxyz" ;
        String fileContentsDeciphered = "File Contents!";
        String[] args = {""};
        String fileList = "01 file1.txt\n" +
                "02 file2.txt\n" +
                "03 file3.txt";

        ProgramControl control = new ProgramControl(mockHandler);
        //UiRequest request = new UiRequest(args[1], args[2])
        //Cannot mock UserInterface.parseArgs because it is static (?)
        when(mockHandler.readFileData(any(UiRequest.class))).thenReturn(fileContentsCiphered);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> control.requestFile(args)
        );
        assertEquals("File ID cannot be empty", exception.getMessage(),
                "requestFile did not throw correct message when one empty arg was passed to it");
    }

    //1 valid arg, 2nd invalid arg is passed
    @Test
    void testRequestFileArg1ValidArg2Invalid() {
        String fileContentsCiphered = "ciphertextxyz" ;
        String fileContentsDeciphered = "File Contents!";
        String[] args = {"1", null};
        String fileList = "01 file1.txt\n" +
                "02 file2.txt\n" +
                "03 file3.txt";

        ProgramControl control = new ProgramControl(mockHandler);
        //UiRequest request = new UiRequest(args[1], args[2])
        //Cannot mock UserInterface.parseArgs because it is static (?)
        when(mockHandler.readFileData(any(UiRequest.class))).thenReturn(fileContentsCiphered);

        assertEquals(fileContentsCiphered, control.requestFile(args),
                "when 1 valid arg was passed with an invalid second arg, requestFile should have returned the ciphered text");
    }

    //1 invalid arg <1 is passed
    @Test
    void testRequestFileArg1LessThanOneArg2Valid() {
        String fileContentsCiphered = "ciphertextxyz" ;
        String fileContentsDeciphered = "File Contents!";
        String[] args = {"0", "valid-cipher-key"};
        String fileList = "01 file1.txt\n" +
                "02 file2.txt\n" +
                "03 file3.txt";

        ProgramControl control = new ProgramControl(mockHandler);
        //UiRequest request = new UiRequest(args[1], args[2])
        //Cannot mock UserInterface.parseArgs because it is static (?)
        when(mockHandler.readFileData(any(UiRequest.class))).thenReturn(fileContentsCiphered);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> control.requestFile(args)
        );
        assertEquals("Invalid File ID (must be greater than 0)", exception.getMessage(),
                "requestFile did not throw correct message when one arg < 1 and a second valid arg was passed to it");
    }

    //1 invalid arg <1 is passed
    @Test
    void testRequestFileArg1NotIntArg2Valid() {
        String fileContentsCiphered = "ciphertextxyz" ;
        String fileContentsDeciphered = "File Contents!";
        String[] args = {"not an integer", "valid-cipher-key"};
        String fileList = "01 file1.txt\n" +
                "02 file2.txt\n" +
                "03 file3.txt";

        ProgramControl control = new ProgramControl(mockHandler);
        //UiRequest request = new UiRequest(args[1], args[2])
        //Cannot mock UserInterface.parseArgs because it is static (?)
        when(mockHandler.readFileData(any(UiRequest.class))).thenReturn(fileContentsCiphered);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> control.requestFile(args)
        );
        assertEquals("File ID must be a number", exception.getMessage(),
                "requestFile did not throw correct message when one arg was not an int and a second valid arg was passed to it");
    }

    //1 invalid arg <1 is passed
    @Test
    void testRequestFileArg1EmptyArg2Valid() {
        String fileContentsCiphered = "ciphertextxyz" ;
        String fileContentsDeciphered = "File Contents!";
        String[] args = {"", "valid-cipher-key"};
        String fileList = "01 file1.txt\n" +
                "02 file2.txt\n" +
                "03 file3.txt";

        ProgramControl control = new ProgramControl(mockHandler);
        //UiRequest request = new UiRequest(args[1], args[2])
        //Cannot mock UserInterface.parseArgs because it is static (?)
        when(mockHandler.readFileData(any(UiRequest.class))).thenReturn(fileContentsCiphered);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> control.requestFile(args)
        );
        assertEquals("File ID cannot be empty", exception.getMessage(),
                "requestFile did not throw correct message when one arg was empty and a second valid arg was passed to it");
    }

    //1 invalid arg <1 is passed
    @Test
    void testRequestFileOverTwoArgs() {
        String fileContentsCiphered = "ciphertextxyz" ;
        String fileContentsDeciphered = "File Contents!";
        String[] args = {"1", "valid-cipher-key", "2"};
        String fileList = "01 file1.txt\n" +
                "02 file2.txt\n" +
                "03 file3.txt";

        ProgramControl control = new ProgramControl(mockHandler);
        //UiRequest request = new UiRequest(args[1], args[2])
        //Cannot mock UserInterface.parseArgs because it is static (?)
        when(mockHandler.readFileData(any(UiRequest.class))).thenReturn(fileContentsCiphered);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> control.requestFile(args)
        );
        assertEquals("Too many command line arguments.", exception.getMessage(),
                "requestFile did not throw correct message 3 args were passed to it");
    }
}