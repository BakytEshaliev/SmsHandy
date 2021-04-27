package smshandy;

public class PrepaidSmsHandy extends SmsHandy{
    private static final int COST_PER_SMS = 10;

    public PrepaidSmsHandy(String number, Provider provider, String name) {
        super(number, provider, name);
        deposit(100);
    }

    public boolean canSendSms() {
        int credits = getProvider().getCreditForSMsHandy(getNumber());
        return credits - COST_PER_SMS >= 0;
    }

    public void deposit(int amount){
        if (amount <= 0)
            throw new IllegalArgumentException("You can not put a negative or zero amount on the deposit");
        getProvider().deposit(getNumber(), amount);
    }

    public void payForSms() {
        int credit = getProvider().getCreditForSMsHandy(getNumber());
        getProvider().getCredits().put(getNumber(), credit - COST_PER_SMS);
    }


    @Override
    public String toString() {
        return super.toString() + String.format("Balance : %d\n", getProvider().getCreditForSMsHandy(getNumber()));
    }
}
