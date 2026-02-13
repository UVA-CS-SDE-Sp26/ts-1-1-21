import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//Using GPT-5 for assistance in testing cases

@ExtendWith(MockitoExtension.class)
class FileHandlerTest {
    private static final Path DATA_FOLDER = Paths.get("data");
    private static final Path CIPHER_FOLDER = Paths.get("ciphers");
    private static final Path KEY_FILE = CIPHER_FOLDER.resolve("key.txt");
    @Mock
    private UiRequest mockRequest;

    @BeforeEach
    void setUp() throws IOException {
        Files.createDirectories(DATA_FOLDER);
        Files.createDirectories(CIPHER_FOLDER);
        Files.writeString(KEY_FILE, "DEFAULTKEY\nFILEKEY\n");
    }

    @AfterEach
    void tearDown() throws IOException {
        deleteFolder(DATA_FOLDER);
        deleteFolder(CIPHER_FOLDER);
    }

    private void deleteFolder(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.walk(path).sorted((a, b) -> b.compareTo(a)).forEach(p -> {
                try {Files.delete(p);
                } catch (IOException ignored) {
                }
            });
        }
    }

    @Test
    void testLoadFilesAndReadFilesCorrectOrdering() throws IOException{
        Files.writeString(DATA_FOLDER.resolve("textb.txt"),"B");
        Files.writeString(DATA_FOLDER.resolve("texta.txt"),"A");
        Files.writeString(DATA_FOLDER.resolve("textc.txt"),"C");
        fileHandler handler = new fileHandler();
        assertEquals("01 texta.txt\n02 textb.txt\n03 textc.txt\n",handler.readFiles());
    }

    @Test
    void testHiddenFilesAreIgnored() throws IOException {
        Files.writeString(DATA_FOLDER.resolve("visible.txt"), "Visible");
        Files.writeString(DATA_FOLDER.resolve(".hidden.txt"), "Hidden");

        fileHandler handler = new fileHandler();
        String result = handler.readFiles();

        assertTrue(result.contains("visible.txt"));
        assertFalse(result.contains(".hidden.txt"));
    }

    @Test
    void testEmptyFolder(){
        //Act on the data
        fileHandler handler = new fileHandler();
        //Assert statement
        assertEquals("", handler.readFiles());
    }

    @Test
    void testNoDataFolder() throws IOException {
        tearDown();

        fileHandler handler = new fileHandler();

        assertEquals("", handler.readFiles());
    }

    @Test
    void testReadFileData_InvalidFileID() {
        when(mockRequest.getFileID()).thenReturn("99");

        fileHandler handler = new fileHandler();
        String result = handler.readFileData(mockRequest);
        assertEquals("Invalid file number.", result);
    }

    @Test
    void testReadFileData_NonNumericID(){
        when(mockRequest.getFileID()).thenReturn("abc");
        fileHandler handler = new fileHandler();
        String result = handler.readFileData(mockRequest);
        assertEquals("Invalid input: Please enter a numeric file ID.", result);
    }


}