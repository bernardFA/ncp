package domain;

import org.joda.time.DateTime;

public class Message extends BaseEntity {

    private String text;
    private DateTime postDateTime;

    public Message(String text) {
        this.text = text;
    }

    public void dateStamp() {
        this.postDateTime = new DateTime();
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return getText();
    }

}
