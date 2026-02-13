import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectControlTest
{
    @Mock
    private FileHandler fileHandler;
    private ProjectControl projectControl;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        projectControl = new ProjectControl(fileHandler);
    }

    @Test
    void retrieve_works() throws IOException
    {
        File dataFolder = new File("data");
        dataFolder.mkdir();
        File testFile = new File(dataFolder, "test.txt");
        testFile.createNewFile();

        when(fileHandler.handle("test.txt"))
                .thenReturn("mocked content");

        String result = projectControl.retrieve(0);
        assertEquals("not done", result);
        verify(fileHandler).handle("test.txt");

        testFile.delete();
        dataFolder.delete();
    }

    @Test
    void retrieve_breaks_all()
    {
        String result = projectControl.retrieve(99);
        assertEquals("Invalid file number.", result);
    }

    @Test
    void retrieve_breaks_wrongKey() throws IOException
    {
        File dataFolder = new File("data");
        dataFolder.mkdir();
        File testFile = new File(dataFolder, "secret.txt");
        testFile.createNewFile();

        when(fileHandler.handle("secret.txt"))
                .thenReturn("mocked encrypted content");

        String result = projectControl.retrieve(0, "wrongKey");
        assertEquals("not done", result);

        verify(fileHandler).handle("secret.txt");
        testFile.delete();
        dataFolder.delete();
    }
}