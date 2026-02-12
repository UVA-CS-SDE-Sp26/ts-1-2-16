import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserInterfaceTest {

    @Test
    void test_argument_handler() {
        String[] args = {"something"};
        String output = UserInterface.argument_handler(args);
        assertEquals("answer", output, "Correct answer should be \"answer\"");
    }

}