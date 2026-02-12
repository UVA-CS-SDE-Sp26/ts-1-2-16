import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectControl
{
    private FileHandler fileHandler;
    private static final String DEFAULT_KEY = "ciphers/key.txt";

    public ProjectControl(FileHandler fileHandler)
    {
        this.fileHandler = fileHandler;
    }

    // list of files (internal use)
    private static List<File> getFiles()
    {
        File folder = new File("data");
        File[] allFiles = folder.listFiles();

        List<File> files = new ArrayList<>();

        StringBuilder output = new StringBuilder();
        int count = 1;

        if (allFiles != null)
        {
            for (File file : allFiles)
            {
                if (file.isFile())
                {
                    files.add(file);
                }
            }
        }
        return files;
    }

    public static String listFiles()
    {
        List<File> files = getFiles();
        if (files.isEmpty())
        {
            return "No files available.";
        }

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < files.size(); i++)
        {
            output.append(String.format("%02d", i + 1))
                    .append(" ")
                    .append(files.get(i).getName())
                    .append("\n");
        }

        return output.toString();
    }

    // retrieve using default key
    public String retrieve(int num)
    {
        return retrieve(num, DEFAULT_KEY);
    }

    // retrieve using input key
    public String retrieve(int num, String key)
    {
        List<File> files = getFiles();

        if (num < 1 || num > files.size())
        {
            return "Invalid file number.";
        }

        String filename = files.get(num - 1).getName();
        String contents = fileHandler.handle(filename);

        try
        {
            Cipher cipher = new Cipher(key);
            return cipher.decrypt(contents);
        } catch (IOException | IllegalArgumentException e) {
            return "Cipher error: " + e.getMessage();
        }
    }
}
