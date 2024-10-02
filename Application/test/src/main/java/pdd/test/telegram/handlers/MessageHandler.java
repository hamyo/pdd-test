package pdd.test.telegram.handlers;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface MessageHandler {
    void handle(Message message);
    boolean canHandle(Message message);
}
