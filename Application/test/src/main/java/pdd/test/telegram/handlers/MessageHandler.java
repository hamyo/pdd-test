package pdd.test.telegram.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandler {
    void handle(Update update);
    boolean canHandle(Update update);
}
