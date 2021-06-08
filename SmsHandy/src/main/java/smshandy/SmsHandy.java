package smshandy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public abstract class SmsHandy {

	protected String number;
	private String name;
	Provider provider;

	private List<Message> sent;
	private List<Message> received;

	/**
	 * Constructor for object of the class SmsHandy
	 * 
	 * @param number   the number from the SmsHandy
	 * @param provider the related provider
	 * @param name     the name of the SmsHandy
	 */
	public SmsHandy(String number, Provider provider, String name) {
		// for simplicity, the number can start with + and following 7-13 numbers.
		if (number == null || !number.trim().matches("^(\\+?)([0-9]){7,13}"))
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

	/**
	 * Sends a message to the receiver via the provider.
	 * 
	 * @param to      the receiver of the message
	 * @param content the content of the message
	 */
	public void sendSms(String to, String content) {
		if (to == null || to.isBlank())
			throw new IllegalArgumentException("The recipient must be set");

		// cant send messages to yourself
		if (to.equals(getNumber()))
			return;

		if (to.equals("*101#")) {
			Integer balance = getProvider().getCredits().get(getNumber());
			System.out.printf("<---The balance on your phone: %d--->%n", balance);
		} else if (canSendSms()) {
			Message message = new Message(content, to, number, new Date());
			if (provider.send(message))
				System.out.println("<---Message sent successfully--->\n");
			else
				System.out.println("<---You can't send sms--->\n");
		} else
			System.out.println("<---You don't have enough funds in your account--->\n");
	}

	/**
	 * Abstract method to check if you can send a message
	 */
	public abstract boolean canSendSms();

	/**
	 * Abstract method to pay for the message
	 */
	public abstract void payForSms();

	/**
	 * Sends a message direct to the receiver without the provider
	 * 
	 * @param peer    the receiver
	 * @param content the content of the message
	 */
	public void sendSmsDirect(SmsHandy peer, String content) {
		if (peer == null)
			throw new IllegalArgumentException("The peer must be set");
		Message message = new Message(content, peer.getNumber(), getNumber(), new Date());
		sent.add(message);
		peer.receiveSms(message);
	}

	/**
	 * Gets a message and saves it
	 * 
	 * @param message the received message
	 */
	public void receiveSms(Message message) {
		received.add(message);
	}

	/**
	 * Shows a list of the sent messages
	 */
	public void listSent() {
		if (sent.size() == 0) {
			System.out.println("<---Sent list is empty--->\n");
			return;
		}
		System.out.println("Sent messages: ");
		for (Message message : sent) {
			System.out.println(message);
		}
	}

	/**
	 * Shows a list of the received messages
	 */
	public void listReceived() {
		if (received.size() == 0) {
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
		provider.deleteSmsHandy(this);
		this.provider = provider;
		provider.register(this);
	}

	public void deleteProvider() {
		this.provider = null;
	}

	/**
	 * Gives a sent message to the related list
	 */
	public void addToSentMessages(Message message) {
		sent.add(message);
	}

	@Override
	public String toString() {
		return String.format("SmsHandy:\n" + "Name: %s\n" + "Number : %s\n" + "Provider name: %s\n", getName(),
				getNumber(), getProvider() != null ? getProvider().getName() : "No Provider");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
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

	public List<Message> getSent() {
		return sent;
	}

	public List<Message> getReceived() {
		return received;
	}
}
