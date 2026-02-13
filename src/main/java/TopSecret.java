public class TopSecret {
    public static void main(String[] args) {
        System.out.println(UserInterface.argument_handler(args));

        // default key
        String[] test1 = {"1"};
        System.out.println(UserInterface.argument_handler(test1));

        // caesar +3 shift
        String[] test2 = {"1", "anotherkey.txt"};
        System.out.println(UserInterface.argument_handler(test2));

    }
}