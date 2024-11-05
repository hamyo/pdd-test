package pdd.test.telegram.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageHandlerFactory {
    private final List<MessageHandler> handlers;

    public void handle(Update update) {
        handlers.stream()
                .filter(handler -> handler.canHandle(update))
                .sorted(Comparator.comparing(MessageHandler::getPriority))
                .forEachOrdered(handler -> handler.handle(update));
    }
}
