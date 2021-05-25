package smshandy;

import java.util.*;
import java.util.List;

public class Provider {

	private String name;
	private Map<String, Integer> credits;

	static List<Provider> providers = new ArrayList<>();

	Map<String, SmsHandy> subscriber;

	/**
	 * Constructor for Provider.
	 * 
	 * @param name the name of the provider
	 */
	public Provider(String name) {
		this.name = name;
		credits = new HashMap<>();
		providers.add(this);
		subscriber = new HashMap<>();
	}

	/**
	 * Add the smsHandy to the map subscriber, when the smsHandy isn't null.
	 * 
	 * @param smsHandy the smsHandy that is to be registered
	 */
	public void register(SmsHandy smsHandy) {
		if (smsHandy == null)
			throw new IllegalArgumentException("The phone must be set");
		subscriber.put(smsHandy.getNumber(), smsHandy);
	}

	/**
	 * Increase the balance from the number.
	 * 
	 * @param number the number which buy new credits
	 * @param amount the bought credits
	 */
	public void deposit(String number, int amount) {
		if (number == null)
			throw new IllegalArgumentException("The number must be set");
		if (credits.containsKey(number)) {
			Integer balance = credits.get(number);
			credits.put(number, balance + amount);
		} else {
			credits.put(number, amount);
		}
	}

	/**
	 * Send the message, pay for the message and return true, if is possible to send
	 * this message.
	 * 
	 * @param message the message to be sent
	 * @return true, if is possible to send the message, otherwise false
	 */
	public boolean send(Message message) {
		if (message == null)
			throw new IllegalArgumentException("The message must be set");

		String sender = message.getFrom();
		String receiver = message.getTo();

		SmsHandy senderHandy = subscriber.get(sender);
		Provider providerFor = findProviderFor(receiver);

		if (senderHandy.getProvider() != null && providerFor != null) {
			senderHandy.addToSentMessages(message);
			SmsHandy receiverHandy = providerFor.subscriber.get(receiver);
			receiverHandy.receiveSms(message);
			senderHandy.payForSms();

			return true;
		}
		return false;
	}

	/**
	 * Return the balance from the number.
	 * 
	 * @param number the to checked number
	 * @return the balance or 0, if the number isn't in the map
	 */
	public int getCreditForSMsHandy(String number) {
		return credits.getOrDefault(number, 0);
	}

	/**
	 * Checks, if the receiver is in the map subscriber.
	 * 
	 * @param receiver the to checked receiver
	 * @return return true, if the receiver is in the map, otherwise false.
	 */
	private boolean canSendTo(String receiver) {
		return subscriber.containsKey(receiver);
	}

	/**
	 * Search the provider for the receiver.
	 * 
	 * @param receiver the receiver, that is checked for the provider
	 * @return the provider from the receiver
	 */
	private static Provider findProviderFor(String receiver) {
		for (Provider provider : providers) {
			if (provider.canSendTo(receiver)) {
				return provider;
			}
		}
		return null;
	}

	/**
	 * Return the map credits.
	 * 
	 * @return the map credits
	 */
	public Map<String, Integer> getCredits() {
		return credits;
	}

	/**
	 * Return the name of the provider.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Provider provider = (Provider) o;
		return Objects.equals(name, provider.name) && Objects.equals(credits, provider.credits)
				&& Objects.equals(subscriber, provider.subscriber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, credits, subscriber);
	}

	/**
	 * Return the subscriber map.
	 * 
	 * @return the map subscriber
	 */
	public Map<String, SmsHandy> getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(Map<String, SmsHandy> subscriber) {
		this.subscriber = subscriber;
	}

	public void setCredits(Map<String, Integer> credits) {
		this.credits = credits;
	}

}
