package pdd.test.telegram.handlers.admin.person;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pdd.test.telegram.handlers.MessageHandler;
import pdd.test.telegram.handlers.TelegramCommand;
import pdd.test.telegram.service.CommonHandler;
import pdd.test.telegram.utils.MessageUtils;

@Component
@RequiredArgsConstructor
public class InactivePersonGetHandler implements MessageHandler {
    private final CommonHandler commonHandler;
    private final TelegramClient telegramClient;

    @SneakyThrows
    @Override
    public void handle(Update update) {
        InlineKeyboardMarkup markup = commonHandler.getPersonsKeyboard(update, TelegramCommand.INACTIVE_USER);
        markup.getKeyboard().add(
            new InlineKeyboardRow(InlineKeyboardButton.builder()
                    .text("⬅")
                    .callbackData(TelegramCommand.USERS.getAction())
                    .build())
        );

        SendMessage response = SendMessage.builder()
                .chatId(MessageUtils.getChatId(update))
                .text("Выберите, пожалуйста, пользователя")
                .replyMarkup(markup)
                .build();
        telegramClient.execute(response);
    }

    @Override
    public boolean canHandle(Update update) {
        return commonHandler.isStrictCommandForAdmin(update, TelegramCommand.INACTIVE_USER);
    }

    @Override
    public boolean canHandle(String action) {
        return TelegramCommand.INACTIVE_USER.getAction().equalsIgnoreCase(action);
    }
}
