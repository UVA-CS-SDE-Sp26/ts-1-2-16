import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ProjectControlTest
{
    private FileHandler fileHandler;
    private ProjectControl projectControl;

    private File dataDir;
    private File cipherDir;

    @BeforeEach
    void setUp() throws IOException
    {
        dataDir = new File("data");
        cipherDir = new File("ciphers");

        dataDir.mkdir();
        cipherDir.mkdir();

        fileHandler = new FileHandler();
        projectControl = new ProjectControl(fileHandler);
    }

    @Test
    void retrieve_works() throws IOException
    {
        File[] files = dataDir.listFiles((dir, name) ->
                name.endsWith(".txt") || name.endsWith(".cip"));

        Arrays.sort(files);


        String result = projectControl.retrieve(1);
        String expected = projectControl.retrieve(1, "key.txt");

        assertEquals(expected, result);
    }

    @Test
    void retrieve_invalid_num()
    {
        String result = projectControl.retrieve(99);
        assertEquals("Invalid file number.", result);
    }

    @Test
    void retrieve_invalid_key() throws IOException
    {
        File[] files = dataDir.listFiles((dir, name) ->
                name.endsWith(".txt") || name.endsWith(".cip"));

        String result = projectControl.retrieve(1, "nonexistent.txt");

        assertTrue(result.contains("Cipher error") || result.contains("Key file not found"));
    }
}