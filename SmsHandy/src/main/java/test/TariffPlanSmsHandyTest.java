package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smshandy.Provider;
import smshandy.TariffPlanSmsHandy;

import static org.junit.jupiter.api.Assertions.*;

public class TariffPlanSmsHandyTest {
    private TariffPlanSmsHandy handy;
    private Provider provider;

    @BeforeEach
    private void beforeAllTests(){
        provider = new Provider("Megacom");
        handy = new TariffPlanSmsHandy("+1111111", provider, "Tom");
    }

    @Test
    public void canSendSmsTest(){
        assertTrue(handy.canSendSms());

        TariffPlanSmsHandy testHandy = new TariffPlanSmsHandy("+2222222", provider, "Anna");
        for (int i = 0; i < 100; i++){
            handy.sendSms(testHandy.getNumber(),"hi");
        }
        assertFalse(handy.canSendSms());
    }

    @Test
    public void payForSmsTest(){
        handy.payForSms();
        assertEquals(99, handy.getRemainingFreeSms());
    }
}
