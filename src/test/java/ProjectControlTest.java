import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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

        File keyFile = new File(cipherDir, "key.txt");
        Files.writeString(keyFile.toPath(), "ABC\n" + "ABC");

        fileHandler = new FileHandler();
        projectControl = new ProjectControl(fileHandler);
    }

    @AfterEach
    void tearDown()
    {
        deleteFolderContents(dataDir);
        deleteFolderContents(cipherDir);
    }

    private void deleteFolderContents(File folder)
    {
        if (folder != null && folder.exists())
        {
            for (File file: folder.listFiles())
            {
                file.delete();
            }
        }
    }

    @Test
    void retrieve_works() throws IOException
    {
        File testFile = new File(dataDir, "test.txt");
        Files.writeString(testFile.toPath(), "ABC");

        String result = projectControl.retrieve(1);

        assertEquals("ABC", result);
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
        File testFile = new File(dataDir, "secret.txt");
        Files.writeString(testFile.toPath(), "ABC");

        String result = projectControl.retrieve(1, "nonexistent.txt");

        assertTrue(result.contains("Cipher error") || result.contains("Key file not found"));
    }
}