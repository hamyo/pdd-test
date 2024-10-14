package pdd.test.telegram.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageHandlerFactory {
    private final List<MessageHandler> handlers;

    public void handle(Update update) {
        handlers.stream()
                .filter(handler -> handler.canHandle(update))
                .findFirst()
                .ifPresent(handler -> handler.handle(update));
    }

    public void handle(String action, Update update) {
        handlers.stream()
                .filter(handler -> handler.canHandle(action))
                .findFirst()
                .ifPresent(handler -> handler.handle(update));
    }
}
