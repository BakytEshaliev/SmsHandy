package smshandy;

import java.util.Date;

public class Message {
    private String content;
    private Date date;
    private String from,to;

    public Message() {
    }

    public Message(String content,String to, String from, Date date) {
        this.content = content;
        this.date = date;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("Content: %s\n" +
                            "From: %s\n" +
                            "To: %s\n" +
                            "Date: %s\n",
                this.content,this.from,this.to,this.date);
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
}
