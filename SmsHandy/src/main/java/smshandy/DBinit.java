package smshandy;

import java.util.ArrayList;
import java.util.List;

public class DBinit {
    static List<PrepaidSmsHandy> allPrepaidHandy = new ArrayList<>();
    static ArrayList<SmsHandy> allHandy = new ArrayList<>();
    static ArrayList<TariffPlanSmsHandy> allTariffHandy = new ArrayList<>();
    static ArrayList<Provider> providers = new ArrayList<>();

    private static DBinit instance;
    private DBinit(){

        providers.add(new Provider("Beeline"));
        providers.add(new Provider("Megacom"));
        providers.add(new Provider("Oshka!"));

        allPrepaidHandy.add(new PrepaidSmsHandy("+1111111", getAllProviders().get(0), "Anna"));
        allPrepaidHandy.add(new PrepaidSmsHandy("+1111112",getAllProviders().get(1),"Bob"));
        allPrepaidHandy.add(new PrepaidSmsHandy("+1111113",getAllProviders().get(2),"Tom"));

        allTariffHandy.add(new TariffPlanSmsHandy("+1111114",getAllProviders().get(0), "Beka"));
        allTariffHandy.add(new TariffPlanSmsHandy("+1111115",getAllProviders().get(1), "Kyla"));
        allTariffHandy.add(new TariffPlanSmsHandy("+1111116",getAllProviders().get(2), "Bema"));
        allHandy.addAll(getAllTariffHandy());
        allHandy.addAll(getAllPrepaidHandy());
    }

    public  List<PrepaidSmsHandy> getAllPrepaidHandy(){
        return allPrepaidHandy;
    }
    public  List<SmsHandy> getAllHandy(){

        return allHandy;
    }
    public  List<TariffPlanSmsHandy> getAllTariffHandy(){
        return allTariffHandy;
    }

    public  List<Provider> getAllProviders(){
        return providers;
    }
    public static DBinit getInstance() {
        if (instance==null) instance = new DBinit();
        return instance;
    }

    public void deletePhone(SmsHandy smsHandy){
        allHandy.remove(smsHandy);
    }
}
