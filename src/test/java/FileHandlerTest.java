import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileHandlerTest {

    private FileHandler fileHandler;

    @BeforeEach
    public void setUp() {
        fileHandler = new FileHandler();
    }

    // Case 1. Request for a file that works and is found within data
    @Test
    public void testHandle_ValidFile() throws IOException {
        String filename = "test.txt";
        // Ensure the file exists
        Path path = Paths.get("data", filename);
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            Files.writeString(path, "This is a test file.\nIt works.");
        }
        
        String content = fileHandler.handle(filename);
        String expectedContent = Files.readString(path);
        
        assertEquals(expectedContent, content, "Should return the full contents of that file");
    }

    // Case 2. Request for a file that exists within project but is not within data
    @Test
    public void testHandle_FileInProjectNotInData() {
        // Using build.gradle.kts as a file we know exists outside of data
        String filename = "../build.gradle.kts"; 
        
        String content = fileHandler.handle(filename);
        
        assertEquals("Path traversal attempt detected", content, "Should prevent access to files outside data directory");
    }

    // Case 3. Request for a file that does not exist
    @Test
    public void testHandle_FileDoesNotExist() {
        String filename = "non_existent_file.txt";
        
        String content = fileHandler.handle(filename);
        
        assertEquals("File Not Found.", content, "Should return 'File Not Found.' for non-existent files");
    }

    // Case 4. Request for a file that doesn't contain text
    @Test
    public void testHandle_NonTextFile() {
        String filename = "test.png"; 
        // Ensure the file exists
        Path path = Paths.get("data", filename);
        try {
            // Create/Overwrite dummy file with invalid UTF-8 bytes to ensure MalformedInputException
            Files.createDirectories(path.getParent());
            Files.write(path, new byte[]{(byte)0xFF, (byte)0xFF, 'P', 'N', 'G'}); 
        } catch (IOException e) {
            e.printStackTrace();
        }

        String content = fileHandler.handle(filename);

        
        boolean isErrorString = content.equals("Insufficient File") || content.contains("File Not Found"); // Covering bases
        // Ideally checking for "Insufficient File"
        
        // If the PNG read succeeds (returns garbage string), it fails this test because we expect graceful failure.
        // But for binary files read as string, usually it throws MalformedInputException.
        
        assertEquals("Insufficient File", content, "Should return 'Insufficient File' for non-text files");
    }
}
