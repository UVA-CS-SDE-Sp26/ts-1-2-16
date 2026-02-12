public class TopSecret {
    void main(String[] args) {
        System.out.println(argument_handler(args));
    }


    public String argument_handler(String[] args){
        if (args.length > 3) {
            return "To many arguments";
        }

        //If no arguments provided, output the list of files in the data folder.
        if (args.length == 1) {
            return ProjectControl.listFiles();
        }

        ProjectControl pc = new ProjectControl(new FileHandler());

        //Convert first argument from a string to the number of a file.
        int file_number;
        try {
            file_number = Integer.parseInt(args[1]);
        } catch (Exception e) {
            return "The first argument wasn't an integer";
        }

        //If one argument provided, output the contents of the specified file
        //using the default key to decipher.
        if (args.length == 2) {
            return pc.retrieve(file_number);
        }

        //If two arguments provided, output the contents of the specified file
        //using the provided key to decipher.
        return pc.retrieve(file_number, args[2]);
    }
}