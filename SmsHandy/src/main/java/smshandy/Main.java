package smshandy;

public class Main {
    public static void main(String[] args) {

        Provider provider1 = new Provider("Beeline");
        Provider provider2 = new Provider("Megacom");

        TariffPlanSmsHandy handy1 = new TariffPlanSmsHandy("+4915256999351", provider1, "Tom");
        TariffPlanSmsHandy handy2 = new TariffPlanSmsHandy("+4915256999352", provider1, "Anna");
        PrepaidSmsHandy handy3 = new PrepaidSmsHandy("+4915256999353", provider2, "Bob");

        for (int i = 0; i < 10; i++) {
            handy3.sendSmsDirect(handy1,"Message №"+i);
        }
//        System.out.println(handy3);
//        handy3.listReceived();
//        handy3.listSent();

//        handy1.listReceived();
//        handy1.listSent();
    }
}
