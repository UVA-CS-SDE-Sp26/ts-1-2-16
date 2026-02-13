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

    // Retrieves a file and decrypts using default key
    @Test
    void retrieve_default_works() throws IOException
    {
        File[] files = dataDir.listFiles((dir, name) ->
                name.endsWith(".txt") || name.endsWith(".cip"));

        Arrays.sort(files);

        String result = projectControl.retrieve(1);

        assertEquals("Carnivore, later renamed DCS1000, was a system implemented by the Federal Bureau of Investigation (FBI) that was\n" +
                "designed to monitor email and electronic communications. It used a customizable packet sniffer that could monitor all\n" +
                "of a target user's Internet traffic. Carnivore was implemented in October 1997. By 2005 it had been replaced with\n" +
                "improved commercial software.", result);
    }

    // Retrieves a file and decrypts using a new key
    @Test
    void retrieve_newkey_works() throws IOException
    {
        File[] files = dataDir.listFiles((dir, name) ->
                name.endsWith(".txt") || name.endsWith(".cip"));

        Arrays.sort(files);


        String result = projectControl.retrieve(1, "anotherkey.txt");
        String expected = projectControl.retrieve(1, "anotherkey.txt");

        assertEquals("Ayplgtmpc, jyrcp pclykcb BAQ9xxx, uyq y qwqrck gknjckclrcb zw rfc Dcbcpyj Zspcys md Gltcqrgeyrgml (DZG) rfyr uyq\n" +
                "bcqgelcb rm kmlgrmp ckygj ylb cjcarpmlga amkkslgayrgmlq. Gr sqcb y asqrmkgXyzjc nyaicr qlgddcp rfyr amsjb kmlgrmp yjj\n" +
                "md y rypecr sqcp'q Glrcplcr rpyddga. Ayplgtmpc uyq gknjckclrcb gl Marmzcp 9775. Zw 0xx3 gr fyb zccl pcnjyacb ugrf\n" +
                "gknpmtcb amkkcpagyj qmdruypc.", result);
    }

    // Retrieves a file with a number that does NOT exist
    @Test
    void retrieve_invalid_num()
    {
        String result = projectControl.retrieve(99);
        assertEquals("Invalid file number.", result);
    }

    // Tries to decrypt with a key that does NOT exist
    @Test
    void retrieve_invalid_key() throws IOException
    {
        File[] files = dataDir.listFiles((dir, name) ->
                name.endsWith(".txt") || name.endsWith(".cip"));

        String result = projectControl.retrieve(1, "nonexistent.txt");

        assertTrue(result.contains("Cipher error") || result.contains("Key file not found"));
    }
}