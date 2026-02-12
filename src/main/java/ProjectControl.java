import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ProjectControl
{
    private FileHandler fileHandler;

    public ProjectControl(FileHandler fileHandler)
    {
        this.fileHandler = fileHandler;
    }

    private static File[] getValidFiles()
    {
        File folder = new File ("data");

        File[] files = folder.listFiles((dir, name) ->
                name.endsWith(".txt") || name.endsWith(".cip"));

        if (files == null)
        {
            return new File[0];
        }

        Arrays.sort(files);
        return files;
    }

    public static String listFiles()
    {
        File[] files = getValidFiles();

        if (files.length == 0)
        {
            return "No files available.";
        }

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < files.length; i++)
        {
            output.append(String.format("%02d %s",
                    i + 1,
                    files[i].getName()))
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }

    // retrieve using default key
    public String retrieve(int num)
    {
        return retrieve(num, "key.txt");
    }

    // retrieve using input key
    public String retrieve(int num, String key)
    {
        File[] files = getValidFiles();

        if (num < 1 || num > files.length)
        {
            return "Invalid file number.";
        }

        String filename = files[num - 1].getName();

        try
        {
            Cipher cipher = new Cipher("ciphers/" + key);
            String encrypted = fileHandler.handle(filename);
            return cipher.decrypt(encrypted);
        } catch (IOException e)
        {
            return "Key file not found.";
        } catch (IllegalArgumentException e)
        {
            return "Cipher error.";
        }
    }
}
