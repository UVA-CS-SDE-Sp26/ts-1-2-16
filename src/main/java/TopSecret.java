
/**
 * Commmand Line Utility
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TopSecret {

  public static void main(String[] args) {

  }

  // String File Name -> String File Contents
  private static String filehandler(String filename) {
    // Checks files only within data folder
    Path dataDir = Paths.get("data").toAbsolutePath();
    Path filePath = dataDir.resolve(filename).toAbsolutePath();

    // If filepath tries accessing parent directories, return invalid traversal
    if (!filePath.startsWith(dataDir)) {
      return "Path traversal attempt detected";
    }

    // Read the file and store into a string
    String content;
    try {
      content = Files.readString(filePath);
    } catch (NoSuchFileException e) {
      content = "File Not Found.";
    } catch (IOException e) {
      content = "Insufficient File";
    }
    return content;
  }

}
