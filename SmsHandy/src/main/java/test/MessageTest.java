package test;

import org.junit.jupiter.api.*;
import smshandy.Message;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageTest {
    private static Message message;

    @BeforeAll
    public static void beforeAllTests(){
        /*provider1 = new Provider("Beeline");
        provider2 = new Provider("Megacom");
        handy1 = new TariffPlanSmsHandy("+4915256999351", provider1, "Tom");
        handy2 = new TariffPlanSmsHandy("+4915256999352", provider1, "Anna");*/
        message = new Message("Test Message","+222222","+111111",new Date());
    }

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void getStringTest(){
        assertEquals("Content: Test Message\n" +
                "From: +111111\n" +
                "To: +222222\nDate: " +
                new Date() + "\n", message.toString());
    }
}
