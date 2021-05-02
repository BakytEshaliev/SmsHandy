package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smshandy.PrepaidSmsHandy;
import smshandy.Provider;
import smshandy.TariffPlanSmsHandy;

import static org.junit.jupiter.api.Assertions.*;

public class PrepaidSmsHandyTest {
    private PrepaidSmsHandy handy;
    private Provider provider;

    @BeforeEach
    private void beforeEachTest(){
        provider = new Provider("Megacom");
        handy = new PrepaidSmsHandy("+1111111", provider, "Tom");
    }

    @Test
    public void canSendSmsTest(){
        assertTrue(handy.canSendSms());
        TariffPlanSmsHandy testHandy = new TariffPlanSmsHandy("+2222222", provider, "Anna");
        for (int i = 0; i < 10; i++){
            handy.sendSms(testHandy.getNumber(), "hi");
        }
        assertFalse(handy.canSendSms());
    }

    @Test
    public void payForSmsTest(){
        handy.payForSms();
        assertEquals(90, provider.getCredits().get(handy.getNumber()));
    }

    @Test
    public void depositTest(){
        handy.deposit(100);
        assertEquals(200, provider.getCredits().get(handy.getNumber()));
    }
}
