package smshandy;

public class PrepaidSmsHandy extends SmsHandy{
    private static final int COST_PER_SMS = 10;

    /**
     * Constructor to create a PrepaidSmsHandy
     * 
     * @param number   the number from the SmsHandy
     * @param provider the related provider
     * @param name     the name of the SmsHandy
     */    
    public PrepaidSmsHandy(String number, Provider provider, String name) {
        super(number, provider, name);
        deposit(100);
    }

    /**
     * Checks if there is enough credits to send a message
     * 
     * @return new amount of credits
     */
    public boolean canSendSms() {
        int credits = getProvider() != null? getProvider().getCreditForSMsHandy(getNumber()) : 0;
        return credits - COST_PER_SMS >= 0;
    }

    /**
     * Deposits an amount of credits in provider
     */
    public void deposit(int amount){
        if (amount <= 0)
            throw new IllegalArgumentException("You can not put a negative or zero amount on the deposit");
        getProvider().deposit(getNumber(), amount);
    }

    /**
     * Decreases amount of credits
     */
    public void payForSms() {
        int credit = getProvider().getCreditForSMsHandy(getNumber());
        getProvider().getCredits().put(getNumber(), credit - COST_PER_SMS);
    }



    @Override
    public String toString() {
        return super.toString() + String.format("Balance : %d\n", getProvider()!=null?getProvider().getCreditForSMsHandy(getNumber()):0);
    }

    public int getBalance(){
        if (provider == null)
            return 0;
        else return getProvider().getCredits().get(getNumber());
    }

    public void setProvider(Provider provider){
        super.setProvider(provider);
        provider.deposit(number, 100);
    }
}
