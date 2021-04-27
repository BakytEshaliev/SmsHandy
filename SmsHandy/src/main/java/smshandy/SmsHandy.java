package smshandy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public abstract class SmsHandy {
    private String number;
    private String name;
    Provider provider;

    private List<Message> sent;
    private List<Message> received;

    public SmsHandy(String number, Provider provider,String name) {
        //for simplicity, the number can start with + and following 9-13 numbers.
        if(number == null || !number.trim().matches("^(\\+?)([0-9]){7,13}"))
            throw new IllegalArgumentException("The number is incorrect");
        if (provider == null)
            throw new IllegalArgumentException("The Provider must be set");
        this.number = number.trim();
        this.provider = provider;
        provider.register(this);
        this.name = name;
        this.sent = new ArrayList<>();
        this.received = new ArrayList<>();
    }
    public void sendSms(String to,String content){
        if (to == null || to.isBlank())
            throw new IllegalArgumentException("The recipient must be set");

        //cant send messages to yourself
        if (to.equals(getNumber()))
            return;

        if (to.equals("*101#")){
            Integer balance = getProvider().getCredits().get(getNumber());
            System.out.printf("<---The balance on your phone: %d--->%n", balance);
        }else if (canSendSms()) {
            Message message = new Message(content, to, number, new Date());
            if (provider.send(message))
                System.out.println("<---Message sent successfully--->\n");
            else
                System.out.println("<---You can't send sms--->\n");
        }else
            System.out.println("<---You don't have enough funds in your account--->\n");
    }
    public abstract boolean canSendSms();

    public abstract void payForSms();

    public void sendSmsDirect(SmsHandy peer, String content){
        if (peer == null)
            throw new IllegalArgumentException("The peer must be set");
        Message message = new Message(content, peer.getNumber(), getNumber(), new Date());
        sent.add(message);
        peer.receiveSms(message);
    }

    public void receiveSms(Message message){
        received.add(message);
    }

    public void listSent(){
        if (sent.size() == 0){
            System.out.println("<---Sent list is empty--->\n");
            return;
        }
        System.out.println("Sent messages: ");
        for (Message message : sent) {
            System.out.println(message);
        }
    }
    public void listReceived(){
        if (received.size() == 0){
            System.out.println("<---Received list is empty--->\n");
            return;
        }
        System.out.println("Received messages: ");
        for (Message message : received) {
            System.out.println(message);
        }
    }

    public String getNumber() {
        return number;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
    public void addToSentMessages(Message message){
        sent.add(message);
    }
    @Override
    public String toString() {
        return String.format(
                "SmsHandy:\n" +
                "Name: %s\n" +
                "Number : %s\n" +
                "Provider name: %s\n",
                getName(),
                getNumber(),
                getProvider().getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmsHandy handy = (SmsHandy) o;
        return Objects.equals(number, handy.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    public String getName() {
        return name;
    }
}
