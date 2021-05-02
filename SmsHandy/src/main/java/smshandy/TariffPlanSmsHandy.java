package smshandy;

public class TariffPlanSmsHandy extends SmsHandy{
    private int remainingFreeSms = 100;

    public TariffPlanSmsHandy(String number, Provider provider, String name) {
        super(number, provider, name);
    }

    public boolean canSendSms() {
        return getRemainingFreeSms() > 0;
    }

    public void payForSms() {
        remainingFreeSms--;
    }

    public int getRemainingFreeSms() {
        return remainingFreeSms;
    }

    @Override
    public void sendSms(String to, String content) {
        if (canSendSms()) {
            payForSms();
            super.sendSms(to, content);
        }
    }

    @Override
    public void sendSmsDirect(SmsHandy peer, String content) {
        if (canSendSms()) {
            payForSms();
            super.sendSmsDirect(peer, content);
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format("remaining free sms: %d\n", remainingFreeSms);
    }

}
