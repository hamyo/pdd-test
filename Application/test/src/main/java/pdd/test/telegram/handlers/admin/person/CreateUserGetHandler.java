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

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CreateUserGetHandler implements MessageHandler {
    private final TelegramClient telegramClient;
    private final CommonHandler commonHandler;

    @SneakyThrows
    @Override
    public void handle(Update update) {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                Collections.singletonList(new InlineKeyboardRow(InlineKeyboardButton.builder()
                        .text("⬅")
                        .callbackData(TelegramCommand.USERS.getAction())
                        .build()))
        );

        SendMessage response = SendMessage.builder()
                .chatId(MessageUtils.getChatId(update))
                .text("Для создания пользователя укажите, пожалуйста, его ФИО через запятую. Если он администратор, укажите, пожалуйста, +.\n\n" +
                        "Пример, Иванов Иван Иванович +")
                .replyMarkup(markup)
                .build();
        telegramClient.execute(response);
    }

    @Override
    public boolean canHandle(String action) {
        return TelegramCommand.CREATE_USER.getAction().equalsIgnoreCase(action);
    }

    @Override
    public boolean canHandle(Update update) {
        return commonHandler.isCommandWithParamsForAdmin(update, TelegramCommand.CREATE_USER);
    }
}
