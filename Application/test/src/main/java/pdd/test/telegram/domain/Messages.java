package pdd.test.telegram.domain;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Messages {
    private List<Object> messages = new ArrayList<>();

    public void add(SendMessage sendMessage) {
        messages.add(sendMessage);
    }

    public void add(SendPhoto sendPhoto) {
        messages.add(sendPhoto);
    }

    public void add(Messages messages) {
        this.messages.addAll(messages.messages);
    }

    public List<Object> getReadOnlyMessages() {
        return Collections.unmodifiableList(messages);
    }
}
