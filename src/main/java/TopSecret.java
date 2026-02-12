public class TopSecret {
    void main(String[] args) {

        if (args.length > 3) {
            System.out.println("To many arguments");
            return;
        }

        //If no arguments provided, output the list of files in the data folder.
        if (args.length == 1) {
            System.out.println(ProjectControl.listFiles());
            return;
        }

        ProjectControl pc = new ProjectControl(new FileHandler());

        //Convert first argument from a string to the number of a file.
        int file_number;
        try {
            file_number = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.out.println("The first argument wasn't an integer");
            return;
        }

        //If one argument provided, output the contents of the specified file
        //using the default key to decipher.
        if (args.length == 2) {
            System.out.println(pc.retrieve(file_number));
            return;
        }

        //If two arguments provided, output the contents of the specified file
        //using the provided key to decipher.
        System.out.println(pc.retrieve(file_number, args[2]));
    }
}