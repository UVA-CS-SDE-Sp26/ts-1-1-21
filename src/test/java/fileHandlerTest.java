import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {
    private static final String DATA_FOLDER = "data";

    @BeforeEach
    void setUp() throws IOException {
        Files.createDirectories(Paths.get(DATA_FOLDER));
    }

    @AfterEach
    void tearDown() throws IOException {
        if (Files.exists(Paths.get(DATA_FOLDER))) {
            Files.walk(Paths.get(DATA_FOLDER))
                    .sorted((a, b) -> b.compareTo(a)) // delete children first
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException ignored) {}
                    });
        }
    }

    @Test
    void testLoadFilesAndReadFilesCorrectOrdering() throws IOException {
        Files.writeString(Paths.get(DATA_FOLDER, "b.txt"), "B");
        Files.writeString(Paths.get(DATA_FOLDER, "a.txt"), "A");
        Files.writeString(Paths.get(DATA_FOLDER, "c.txt"), "C");


        fileHandler handler = new fileHandler();
        String result = handler.readFiles();

        //files should be sorted by index 01, 02, 03.
        assertEquals(
                "01 a.txt\n" +
                        "02 b.txt\n" +
                        "03 c.txt\n",
                result
        );
    }

    @Test
    void testHiddenFilesAreIgnored() throws IOException {
        Files.writeString(Paths.get(DATA_FOLDER, "visible.txt"), "Visible");
        Files.writeString(Paths.get(DATA_FOLDER, ".hidden.txt"), "Hidden");

        fileHandler handler = new fileHandler();
        String result = handler.readFiles();

        assertTrue(result.contains("visible.txt"));
        assertFalse(result.contains(".hidden.txt"));
    }

    @Test
    void testNoDataFolder() throws IOException { //Expected: delete
        tearDown(); // delete folder before test

        // Act
        fileHandler handler = new fileHandler();
        String result = handler.readFiles();

        // Assert
        assertEquals("", result); // no files loaded
    }

    @Test
    void testEmptyFolder() {
        // Act
        fileHandler handler = new fileHandler();
        String result = handler.readFiles();

        // Assert
        assertEquals("", result);
    }
}