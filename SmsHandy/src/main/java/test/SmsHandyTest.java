package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import smshandy.Message;
import smshandy.Provider;
import smshandy.TariffPlanSmsHandy;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SmsHandyTest {
    private static TariffPlanSmsHandy handy;
    private static Provider provider;

    @BeforeAll
    private static void beforeAllTests(){
        provider = new Provider("Megacom");
        handy = new TariffPlanSmsHandy("+1111111", provider, "Tom");
    }

    @Test
    public void sendSmsTest(){
        TariffPlanSmsHandy testHandy = new TariffPlanSmsHandy("+2222222", provider, "Anna");
        handy.sendSms(testHandy.getNumber(), "hi");
        Message expectedMessage = new Message("hi",testHandy.getNumber(),handy.getNumber(),new Date());

        assertTrue(handy.getSent().contains(expectedMessage));
        assertTrue(testHandy.getReceived().contains(expectedMessage));
    }

    @Test
    public void sendSmsDirectTest(){
        TariffPlanSmsHandy testHandy = new TariffPlanSmsHandy("+2222222", provider, "Anna");
        handy.sendSmsDirect(testHandy,"hi");
        Message expectedMessage = new Message("hi",testHandy.getNumber(),handy.getNumber(),new Date());

        assertTrue(handy.getSent().contains(expectedMessage));
        assertTrue(testHandy.getReceived().contains(expectedMessage));
    }

    @Test
    public void receiveSmsTest(){
        Message expectedMessage = new Message("hi",handy.getNumber(),handy.getNumber(),new Date());
        handy.receiveSms(expectedMessage);
        assertTrue(handy.getReceived().contains(expectedMessage));
    }

    @Test
    public void listSendTest(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        TariffPlanSmsHandy testHandy = new TariffPlanSmsHandy("+2222222", provider, "Anna");
        handy.sendSmsDirect(testHandy,"hi");
        Message expectedMessage = new Message("hi",testHandy.getNumber(),handy.getNumber(),new Date());
        handy.listSent();
        assertEquals("Sent messages: \n"+expectedMessage.toString() +"\n", outContent.toString());
    }

    @Test
    public void listReceivedTest(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        TariffPlanSmsHandy testHandy = new TariffPlanSmsHandy("+2222222", provider, "Anna");
        handy.sendSmsDirect(testHandy,"hi");
        Message expectedMessage = new Message("hi",testHandy.getNumber(),handy.getNumber(),new Date());
        testHandy.listReceived();
        assertEquals("Received messages: \n"+expectedMessage.toString() +"\n", outContent.toString());
    }
}
