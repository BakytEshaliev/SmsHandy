package smshandy;


import java.awt.*;
import java.util.*;
import java.util.List;

public class Provider {
    private String name;
    private Map<String,Integer> credits;

    static List<Provider> providers = new ArrayList<>();

    Map<String, SmsHandy> subscriber;

    public Provider(String name) {
        this.name = name;
        credits = new HashMap<>();
        providers.add(this);
        subscriber = new HashMap<>();
    }


    public void register(SmsHandy smsHandy){
        subscriber.put(smsHandy.getNumber(), smsHandy);
    }
    public void deposit(String number, int amount){
        if (credits.containsKey(number)) {
            Integer balance = credits.get(number);
            credits.put(number, balance + amount);
        }else {
            credits.put(number, amount);
        }
    }
    public boolean send(Message message){

        String sender = message.getFrom();
        String receiver = message.getTo();

        SmsHandy senderHandy = subscriber.get(sender);

        if (receiver.equals("*101#")){

            Integer balance = credits.get(sender);
            Message balanceMsg = new Message("Balance on your phone: " + balance, sender, "*101#", new Date());
            senderHandy.receiveSms(balanceMsg);

            return true;
        }else if(receiver != null && !receiver.isBlank() &&
                senderHandy.getProvider() != null && findProviderFor(receiver) != null){
            senderHandy.addToSentMessages(message);
            SmsHandy receiverHandy = findProviderFor(receiver).subscriber.get(receiver);
            receiverHandy.receiveSms(message);
            senderHandy.payForSms();

            return true;
        }
        return false;
    }


    public int getCreditForSMsHandy(String number){
        return credits.getOrDefault(number,0);
    }

    private boolean canSendTo(String receiver){
        return subscriber.containsKey(receiver);
    }

    private static Provider findProviderFor(String receiver){
        for (Provider provider : providers) {
            if (provider.canSendTo(receiver)) {
                return provider;
            }
        }
        return null;
    }

    public Map<String, Integer> getCredits() {
        return credits;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;
        return Objects.equals(name, provider.name) &&
                Objects.equals(credits, provider.credits) &&
                Objects.equals(subscriber, provider.subscriber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, credits, subscriber);
    }
}
