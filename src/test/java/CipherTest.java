import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CipherTest {
    @Test
    public void testIncorrectFilePath() throws IOException{
        Assertions.assertThrows(NoSuchFileException.class, () -> {
            String keyPath = "ciphers/key";
            Cipher cipher = new Cipher(keyPath);
        });
    }

    @Test
    public void testDecipherWithShift() throws IOException {
        String keyPath = "ciphers/key.txt";
        Cipher cipher = new Cipher(keyPath);

        // Using your specific key: 'b' is the cipher for 'a'
        // Input "bcDE" should return "abCD"
        String encrypted = "bcDE";
        String expected = "abCD";

        assertEquals(expected, cipher.decrypt(encrypted), "The deciphered text should match the plain text mapping.");
    }
    @Test
    public void testDecipherWithSpace() throws IOException {
        String keyPath = "ciphers/key.txt";
        Cipher cipher = new Cipher(keyPath);

        // Using your specific key: 'b' is the cipher for 'a'
        // Input "bcde" should return "abcd"
        String encrypted = "bc DE";
        String expected = "ab CD";

        assertEquals(expected, cipher.decrypt(encrypted), "The deciphered text should match the plain text mapping, despite space.");
    }
}
