package pdd.test.telegram;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pdd.test.telegram.handlers.MessageHandler;
import pdd.test.utils.BusinessException;

import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class TestBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    @Value("${telegram.bot.token}") private String token;
    private final TelegramClient telegramClient;
    private final List<MessageHandler> handlers;

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();

            try {
                try {
                    handlers.stream()
                            .filter(handler -> handler.canHandle(update.getMessage()))
                            .findFirst()
                            .ifPresent(handler -> handler.handle(update.getMessage()));
                } catch (BusinessException ex) {
                    handleBusinessError(ex, chatId);
                }
            } catch (TelegramApiException e) {
                log.error("Error on getting message. ", e);
            }
        }
    }

    private void handleBusinessError(BusinessException ex, long chatId) throws TelegramApiException {
        SendMessage message = SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text(ex.getMessage())
                .build();
        telegramClient.execute(message);
    }
}
