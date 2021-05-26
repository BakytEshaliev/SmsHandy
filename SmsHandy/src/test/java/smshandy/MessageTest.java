package smshandy;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import smshandy.Message;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageTest {
    private static Message message;

    @BeforeAll
    private static void beforeAllTests(){
        message = new Message("Test Message","+222222","+111111",new Date());
    }

    @Test
    public void getStringTest(){
        assertEquals("Content: Test Message\n" +
                "From: +111111\n" +
                "To: +222222\nDate: " +
                message.getDate() + "\n", message.toString());
    }
}
