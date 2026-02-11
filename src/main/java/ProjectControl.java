import java.io.File;

public class ProjectControl
{
    private static FileHandler fileHandler = new FileHandler();

    // numbered list of files
    public static String listFiles()
    {
        File folder = new File("data");
        File[] files = folder.listFiles();

        if (files == null || files.length == 0)
        {
            return "No files available.";
        }

        StringBuilder output = new StringBuilder();
        int count = 1;

        for (File file : files)
        {
            if (file.isFile())
            {
                output.append(String.format("%02d", count))
                        .append(" ")
                        .append(file.getName())
                        .append("\n");
                count++;
            }
        }
        return output.toString();
    }

    // retrieve using default key
    public static String retrieve(int num)
    {
        File folder = new File("data");
        File[] files = folder.listFiles();

        if (files == null || num < 0 || num >= files.length)
        {
            return "Invalid file number.";
        }

        String filename = files[num].getName();
        String contents = fileHandler.handle(filename);

        return "not done"; // change to form of "Cipher.decipher(contents);"
    }

    // retrieve using input key
    public static String retrieve(int num, String key)
    {
        File folder = new File("data");
        File[] files = folder.listFiles();

        if (files == null || num < 0 || num >= files.length)
        {
            return "Invalid file number.";
        }

        String filename = files[num].getName();
        String contents = fileHandler.handle(filename);

        return "not done"; // change to form of "Cipher.decipher(contents, key);"
    }

}
