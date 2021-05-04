package smshandy;

import java.util.Date;
import java.util.Objects;

public class Message {

	private String content;
	private Date date;
	private String from, to;

	/**
	 * Default constructor for Message
	 */
	public Message() {
	}

	/**
	 * Constructor for Message with 4 parameters.
	 * 
	 * @param content the content of the message
	 * @param to      the receiver of the message
	 * @param from    the sender of the message
	 * @param date    the date when the message is created
	 */
	public Message(String content, String to, String from, Date date) {
		if (from == null)
			throw new IllegalArgumentException("The sender's number must be set");
		this.content = content;
		this.date = date;
		this.from = from;
		this.to = to;
	}

	@Override
	public String toString() {
		return String.format("Content: %s\n" + "From: %s\n" + "To: %s\n" + "Date: %s\n", this.content, this.from,
				this.to, this.date);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Message message = (Message) o;
		return Objects.equals(content, message.content) && Objects.equals(date, message.date)
				&& Objects.equals(from, message.from) && Objects.equals(to, message.to);
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, date, from, to);
	}
}
