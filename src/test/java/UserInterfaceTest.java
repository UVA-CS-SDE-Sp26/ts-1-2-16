import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserInterfaceTest {

    @Test
    void test_argument_handler() {
        String[] test1 = {"argument1", "argument2", "argument3"};
        String output = UserInterface.argument_handler(test1);
        assertEquals("To many arguments", output, "Correct answer should be \"To many arguments\"");

        String[] test2 = {"string"};
        output = UserInterface.argument_handler(test2);
        assertEquals("The first argument wasn't an integer", output, "Correct answer should be \"The first argument wasn't an integer\"");

        String[] test3 = {"string", "cipher key"};
        output = UserInterface.argument_handler(test3);
        assertEquals("The first argument wasn't an integer", output, "Correct answer should be \"The first argument wasn't an integer\"");

        //This assumes that either the "data" directory doesn't exist, or that it is empty.
        String[] test4 = {"0"};
        output = UserInterface.argument_handler(test4);
        assertEquals("Invalid file number.", output, "Correct answer should be \"Invalid file number.\"");

        //This assumes that either the "data" directory doesn't exist, or that it is empty.
        String[] test5 = {"0", "cipher key"};
        output = UserInterface.argument_handler(test5);
        assertEquals("Invalid file number.", output, "Correct answer should be \"Invalid file number.\"");

    }

}