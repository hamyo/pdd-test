package pdd.test.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pdd.test.telegram.handlers.StartHandler;
import pdd.test.utils.BusinessException;


@Component
@Slf4j
public class TestBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;
    private final BotConfiguration config;
    private final StartHandler startHandler;

    public TestBot(BotConfiguration config, StartHandler startHandler) {
        this.config = config;
        this.telegramClient = new OkHttpTelegramClient(getBotToken());
        this.startHandler = startHandler;
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            long userId = update.getMessage().getFrom().getId();

            try {
                try {
                    if (messageText.startsWith("/start")) {
                        telegramClient.execute(startHandler.handle(update.getMessage()));
                        return;
                    }
                } catch (BusinessException ex) {
                    handleError(ex, chatId);
                }
            } catch (TelegramApiException e) {
                log.error("Error on getting message. ", e);
            }
        }
    }

    private void handleError(BusinessException ex, long chatId) throws TelegramApiException {
        SendMessage message = SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text(ex.getMessage())
                .build();
        telegramClient.execute(message);
    }
}
