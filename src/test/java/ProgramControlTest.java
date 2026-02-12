/**
 * ProgramControlTest
 * @author Rory McGuire
 * Contains JUnit tests for the class ProgramControl
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class ProgramControlTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private AutoCloseable closeable;

    @Mock
    private fileHandler handler;

    @BeforeEach
    public void setUp() {
        //set up mocks
        closeable = MockitoAnnotations.openMocks(this);
        //set up catch-stream for print output
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() throws Exception {
        //teardown mocks
        closeable.close();
        //teardown catch-stream
        System.setOut(originalOut);
    }

    //fileHandler.listFiles returns a String
    @Test
    void testListFilesWithFiles() {
        String fileList = "01 file1.txt\n" +
                "02 file2.txt\n" +
                "03 file3.txt";

        ProgramControl control = new ProgramControl(handler);
        when(handler.readFiles()).thenReturn(fileList);
        control.listFiles();
        assertEquals(fileList + System.lineSeparator(), outputStream.toString(),
                "listFiles() printed incorrect value to out");
    }

    //fileHandler.listFiles returns null
    @Test
    void testListFilesWithoutFiles() {
        String fileList = null;

        ProgramControl control = new ProgramControl(handler);
        when(handler.readFiles()).thenReturn(fileList);
        control.listFiles();
        assertEquals(fileList + System.lineSeparator(), outputStream.toString(),
                "listFiles() printed incorrect value to out");
    }

    //valid arg is passed
    @Test
    void testRequestFileWithInput() {
        String fileContents = "File Contents!";
        String[] args = {"1"};
        String fileList = "01 file1.txt\n" +
                "02 file2.txt\n" +
                "03 file3.txt";

        ProgramControl control = new ProgramControl(handler);
        //when(handler.readFileData()).thenReturn(fileContents);

        //when(handler.readFiles()).thenReturn(fileList);
        //control.listFiles();
        //assertEquals(fileList + System.lineSeparator(), outputStream.toString(),
        //        "listFiles() printed incorrect value to out");
        assertEquals(fileContents, control.requestFile(args), "");
    }
}