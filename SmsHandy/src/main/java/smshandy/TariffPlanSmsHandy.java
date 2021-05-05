package smshandy;

public class TariffPlanSmsHandy extends SmsHandy{
    private int remainingFreeSms = 100;

    /**
     * Constructor to create a TariffPlanSmsHandy
     * 
     * @param number   the number from the SmsHandy
     * @param provider the related provider
     * @param name     the name of the SmsHandy
     */    
    public TariffPlanSmsHandy(String number, Provider provider, String name) {
        super(number, provider, name);
    }

    /**
     * Checks if smsHandy can send a message for free
     * 
     * @return the remainingFreeSms if there is at least one
     */
    public boolean canSendSms() {
        return getRemainingFreeSms() > 0;
    }

    /**
     * Decreases the amount of the remainingFreeSms
     */
    public void payForSms() {
        remainingFreeSms--;
    }

    /**
     * Provides the amount of remainingFreeSms
     * 
     * @return the amount of remaingFreeSms
     */
    public int getRemainingFreeSms() {
        return remainingFreeSms;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("remaining free sms: %d\n", remainingFreeSms);
    }

}
