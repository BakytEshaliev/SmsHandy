package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import smshandy.PrepaidSmsHandy;
import smshandy.Provider;

import static org.junit.jupiter.api.Assertions.*;

public class PrepaidSmsHandyTest {
    private static PrepaidSmsHandy handy;
    private static Provider provider;

    @BeforeAll
    private static void beforeAllTests(){
        provider = new Provider("Megacom");
        handy = new PrepaidSmsHandy("+1111111", provider, "Tom");
    }

    @Test
    public void canSendSmsTest(){
        assertFalse(handy.canSendSms());
        handy.deposit(100);
        assertTrue(handy.canSendSms());
    }

    @Test
    public void payForSmsTest(){
        provider.deposit(handy.getNumber(),100);
        handy.payForSms();
        assertEquals(90, provider.getCredits().get(handy.getNumber()));
    }

    @Test
    public void depositTest(){
        handy.deposit(100);
        assertEquals(100, provider.getCredits().get(handy.getNumber()));
    }
}
