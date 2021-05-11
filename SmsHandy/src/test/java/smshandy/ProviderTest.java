package smshandy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smshandy.Message;
import smshandy.PrepaidSmsHandy;
import smshandy.Provider;
import smshandy.TariffPlanSmsHandy;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProviderTest {

    private static Provider provider;

    @BeforeAll
    private static void beforeAllTests(){
        provider = new Provider("Megacom");
    }

    @AfterEach
    private void afterEachTest(){
        provider.setCredits(new HashMap<>());
    }

    @Test
    public void sendNullMessageTest(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            provider.send(null);
        });

        String expectedMessage = "The message must be set";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void sendTest(){
        Message m = new Message("Test Message","+2222222","+1111111",new Date());
        TariffPlanSmsHandy handy1 = new TariffPlanSmsHandy("+1111111", provider, "Tom");
        TariffPlanSmsHandy handy2 = new TariffPlanSmsHandy("+2222222", provider, "Anna");
        assertTrue(provider.send(m));
    }

    @Test
    public void registerTest(){
        TariffPlanSmsHandy handy = new TariffPlanSmsHandy("+1111111", provider, "Tom");
        assertTrue(provider.getSubscriber().containsValue(handy));
    }

    @Test
    public void depositTest(){
        TariffPlanSmsHandy handy = new TariffPlanSmsHandy("+1111111", provider, "Tom");
        provider.deposit(handy.getNumber(),100);
        assertEquals(provider.getCredits().get(handy.getNumber()), 100);
    }

    @Test
    public void getCreditForSmsHandyTest(){
        PrepaidSmsHandy handy = new PrepaidSmsHandy("+1111111", provider, "Tom");
        assertEquals(provider.getCreditForSMsHandy(handy.getNumber()), 100);
    }
}
