package smshandy;

import java.util.ArrayList;
import java.util.List;

public class DBinit {
	static List<PrepaidSmsHandy> allPrepaidHandy = new ArrayList<>();
	static ArrayList<SmsHandy> allHandy = new ArrayList<>();
	static ArrayList<TariffPlanSmsHandy> allTariffHandy = new ArrayList<>();
	static ArrayList<Provider> providers = new ArrayList<>();

	private static DBinit instance;

	private DBinit() {

		providers.add(new Provider("Beeline"));
		providers.add(new Provider("Megacom"));
		providers.add(new Provider("Oshka!"));

		allPrepaidHandy.add(new PrepaidSmsHandy("+1111111", getAllProviders().get(0), "Anna"));
		allPrepaidHandy.add(new PrepaidSmsHandy("+1111112", getAllProviders().get(1), "Bob"));
		allPrepaidHandy.add(new PrepaidSmsHandy("+1111113", getAllProviders().get(2), "Tom"));

		allTariffHandy.add(new TariffPlanSmsHandy("+1111114", getAllProviders().get(0), "Beka"));
		allTariffHandy.add(new TariffPlanSmsHandy("+1111115", getAllProviders().get(1), "Kyla"));
		allTariffHandy.add(new TariffPlanSmsHandy("+1111116", getAllProviders().get(2), "Bema"));
		allHandy.addAll(getAllTariffHandy());
		allHandy.addAll(getAllPrepaidHandy());
	}

	/**
	 * Return all prepaid handys
	 * 
	 * @return a list with all prepaid handys
	 */
	public List<PrepaidSmsHandy> getAllPrepaidHandy() {
		return allPrepaidHandy;
	}

	/**
	 * Return all prepaid and tariffplan handys
	 * 
	 * @return a list with all prepaid and tariffplan handys
	 */
	public List<SmsHandy> getAllHandy() {
		return allHandy;
	}

	/**
	 * Return all tariffplan handys
	 * 
	 * @return a list with all tariffplan handys
	 */
	public List<TariffPlanSmsHandy> getAllTariffHandy() {
		return allTariffHandy;
	}

	/**
	 * Return all providers
	 * 
	 * @return a list with all providers
	 */
	public List<Provider> getAllProviders() {
		return providers;
	}

	public static DBinit getInstance() {
		if (instance == null)
			instance = new DBinit();
		return instance;
	}

	/**
	 * Removes a smsHandy from the list
	 * 
	 * @param smsHandy the handy, that to be deleted
	 */
	public void deletePhone(SmsHandy smsHandy) {
		allHandy.remove(smsHandy);
	}

	/**
	 * Return a provider with the specific name or null when the provider not exist
	 * 
	 * @param name the name of a provider
	 * @return the provider with the specific name
	 */
	public Provider findProviderByName(String name) {
		return providers.stream().filter(e -> e.getName().equals(name)).findAny().get();
	}

	public void addHandy(SmsHandy smsHandy){
		if (smsHandy instanceof TariffPlanSmsHandy) {
			allTariffHandy.add((TariffPlanSmsHandy) smsHandy);
		} else {
			allPrepaidHandy.add((PrepaidSmsHandy) smsHandy);
		}
		allHandy.add(smsHandy);
	}
}
